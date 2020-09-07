package com.wata.payslip.service.Implements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wata.payslip.converter.ProjectConverter;
import com.wata.payslip.model.dtos.EmployeeDTO;
import com.wata.payslip.model.dtos.ProjectDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.ProjectEntity;
import com.wata.payslip.model.entity.TypeProjectEntity;
import com.wata.payslip.repository.EmployeeRepository;
import com.wata.payslip.repository.ProjectRepository;
import com.wata.payslip.repository.TypeProjectRepository;
import com.wata.payslip.service.Interface.IProjectService;
import com.wata.payslip.utils.PagingUtil;
import com.wata.payslip.utils.SortUtil;

@Service
public class ProjectService implements IProjectService {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	TypeProjectRepository typeProjectRepository;

	@Autowired
	private ProjectConverter projectConverter;

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseEntity<Map<String, Object>> createProject(ProjectDTO projectDTO) {

		ProjectEntity entity = projectConverter.toEntity(projectDTO);
		try {
			TypeProjectEntity typeProjectEntity = entityManager.getReference(TypeProjectEntity.class,
					projectDTO.getIdTypeProject());
			entity.setTypeProject(typeProjectEntity);
			entity = projectRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("Project", projectConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllProject() {
		try {
			List<ProjectDTO> dtoes = projectConverter.toDTOs(projectRepository.findAll());

			Map<String, Object> response = new HashMap<>();
			response.put("Projects", dtoes);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getProjectById(Integer idProject) {
		try {
			ProjectEntity entity = projectRepository.findByIdProject(idProject).get();
			Map<String, Object> response = new HashMap<>();
			ProjectDTO project = projectConverter.toDTO(entity);
			Set<EmployeeEntity> employee = entity.getEmployee();
			List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
			for (EmployeeEntity user : employee) {
				employees.add(employeeService.convertToEmployeeDTO(user));
			}
			project.setEmployee(employees);
			response.put("Projects", project);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteProjectById(Integer idProject) {
		try {
			projectRepository.deleteById(idProject);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> updateProjectById(ProjectDTO projectDTO, Integer idProject) {

		if (!(projectDTO.getIdProject().equals(idProject))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (projectRepository.findOneByIdProject(idProject) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// ---------------------------------------------------------------------
		ProjectEntity entity = projectRepository.findByIdProject(idProject).get();
		entity = projectConverter.toEntity(projectDTO);
		entity.setIdProject(idProject);
		try {
			TypeProjectEntity typeProjectEntity = entityManager.getReference(TypeProjectEntity.class,
					projectDTO.getIdTypeProject());
			entity.setTypeProject(typeProjectEntity);
			entity = projectRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("Project", projectConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> searchByNameProject(SearchData searchData) {
		String nameProject = searchData.getSearchValue();
		try {
			List<ProjectEntity> projectEntities = new ArrayList<ProjectEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<ProjectEntity> pageTuts;
			pageTuts = (nameProject == null) ? projectRepository.findAll(paging)
					: projectRepository.findByNameProjectContaining(nameProject.trim(), paging);
			projectEntities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("Project", projectConverter.toDTOs(projectEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getProjectByEmployee(int id) {
		Map<String, Object> response = new HashMap<>();
		List<ProjectDTO> project = new ArrayList<>();
		Set<ProjectEntity> entity = employeeRepository.findById(id).get().getProjectEntity();
		for (ProjectEntity getProject : entity) {
			project.add(projectConverter.toDTO(getProject));
		}
		response.put("project", project);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

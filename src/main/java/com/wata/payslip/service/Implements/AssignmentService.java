package com.wata.payslip.service.Implements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wata.payslip.model.dtos.AssignmentDTO;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.ProjectEntity;
import com.wata.payslip.repository.EmployeeRepository;
import com.wata.payslip.repository.ProjectRepository;
import com.wata.payslip.service.Interface.IAssignmentService;

@Service
public class AssignmentService implements IAssignmentService {
	@Autowired
	public EmployeeRepository employeeRepository;
	@Autowired
	public ProjectRepository projectRepository;

	@Override
	public AssignmentDTO convertToEmployeeDTO(EmployeeEntity user) {
		AssignmentDTO assignment = new AssignmentDTO();
		for (ProjectEntity project : user.getProjectEntity()) {
			project.setEmployee(null);
		}
		Set<EmployeeEntity> assignEntity = new HashSet<EmployeeEntity>();
		assignEntity.add(employeeRepository.findById(user.getId()).get());
		user.setAccount(null);
		assignment.setIdEmployee(user.getId());
		assignment.setProject(null);
		assignment.setIdProject(null);
		assignment.setEmployee(assignEntity);
		return assignment;
	}

	@Override
	public AssignmentDTO convertToProjectDTO(ProjectEntity user) {
		AssignmentDTO assignment = new AssignmentDTO();

		for (EmployeeEntity entity : user.getEmployee()) {
			entity.setProjectEntity(null);
			entity.setAccount(null);
		}
		Set<ProjectEntity> assignProject = new HashSet<ProjectEntity>();
		assignProject.add(projectRepository.findOneByIdProject(user.getIdProject()).get());
		assignment.setIdProject(user.getIdProject());
		assignment.setEmployee(null);
		assignment.setIdEmployee(null);
		assignment.setProject(assignProject);
		return assignment;
	}

	@Override
	public List<AssignmentDTO> getAllEmployee() {
		return ((List<EmployeeEntity>) employeeRepository.findAll()).stream().map(this::convertToEmployeeDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AssignmentDTO> getByProject() {
		return ((List<ProjectEntity>) projectRepository.findAll()).stream().map(this::convertToProjectDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<AssignmentDTO> getByIdEmployee(Integer id) {
		return (employeeRepository.findById(id)).map(this::convertToEmployeeDTO);

	}

	@Override
	public Optional<AssignmentDTO> getByIdProject(Integer id) {
		return (projectRepository.findOneByIdProject(id)).map(this::convertToProjectDTO);
	}

	@Override
	public ResponseEntity<?> Assignment(AssignmentDTO assignment) {
		if (!projectRepository.findById(assignment.getIdProject()).isPresent()
				&& !employeeRepository.findById(assignment.getIdEmployee()).isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		;
		// --------------------------------------------------------------------------
		ProjectEntity project = projectRepository.findById(assignment.getIdProject()).get();
		EmployeeEntity employee = employeeRepository.findById(assignment.getIdEmployee()).get();
		// Create data assignment here-------------------------------------------------
		project.getEmployee().add(employeeRepository.findById(assignment.getIdEmployee()).get());
		employee.getProjectEntity().add(projectRepository.findById(assignment.getIdProject()).get());
		employeeRepository.save(employee);
		// --------------------------------------------------------------------------------------
		project.setEmployee(null);
		employee.setProjectEntity(null);
		employee.setAccount(null);
		Map<String, Object> response = new HashMap<>();
		response.put("idProject", assignment.getIdProject());
		response.put("idEmployee", assignment.getIdEmployee());
		response.put("project", project);
		response.put("employee", employee);
		return ResponseEntity.ok(response);

	}

	@Override
	public ResponseEntity<?> Delete(AssignmentDTO assignment) {
		if (!projectRepository.findById(assignment.getIdProject()).isPresent()
				&& !employeeRepository.findById(assignment.getIdEmployee()).isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		// --------------------------------------------------------------------------
		ProjectEntity project = projectRepository.findById(assignment.getIdProject()).get();
		EmployeeEntity employee = employeeRepository.findById(assignment.getIdEmployee()).get();
		// ----------------------remove assignment data
		employee.getProjectEntity().remove(employee.getProjectEntity());
		for (ProjectEntity user : employee.getProjectEntity()) {
			user.getEmployee().remove(employee);
		}
		employeeRepository.save(employee);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}

package com.wata.payslip.service.Implements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wata.payslip.converter.WorkConverter;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.WorkDTO;
import com.wata.payslip.model.entity.ProjectEntity;
import com.wata.payslip.model.entity.WorkEntity;
import com.wata.payslip.repository.WorkRepository;
import com.wata.payslip.service.Interface.IWorkService;
import com.wata.payslip.utils.ApplicationSettings;
import com.wata.payslip.utils.PagingUtil;
import com.wata.payslip.utils.SortUtil;

@Service
public class WorkService implements IWorkService {
	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private WorkConverter workConverter;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseEntity<Map<String, Object>> getAllWork() {
		try {
			List<WorkDTO> dtoes = workConverter.toDTOs(workRepository.findAll());
			Map<String, Object> response = new HashMap<>();
			response.put("Works", dtoes);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getWorkById(Integer idWork) {
		try {
			WorkEntity entity = workRepository.findOneByIdWork(idWork);
			Map<String, Object> response = new HashMap<>();
			response.put("Work", workConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteWorkById(Integer idWork) {
		try {
			workRepository.deleteById(idWork);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateWorkById(WorkDTO workDTO, Integer idWork) {
		WorkEntity entity = workConverter.toEntity(workDTO);
		entity.setIdWork(idWork);
		try {
			ProjectEntity projectEntity = entityManager.getReference(ProjectEntity.class, workDTO.getIdProject());
			entity.setProjectEntity(projectEntity);
			entity = workRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("Work", workConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchWorkBySummary(SearchData searchData) {
		String summary = searchData.getSearchValue();
		try {
			List<WorkEntity> workEntities = new ArrayList<WorkEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<WorkEntity> pageTuts;
			pageTuts = (summary == null) ? workRepository.findAll(paging)
					: workRepository.findBySummaryContaining(summary.trim(), paging);
			workEntities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse(ApplicationSettings.WORK, workConverter.toDTOs(workEntities),
					pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> createWork(WorkDTO workDTO) {
		WorkEntity entity = workConverter.toEntity(workDTO);
		try {
			ProjectEntity projectEntity = entityManager.getReference(ProjectEntity.class, workDTO.getIdProject());
			entity.setProjectEntity(projectEntity);
			entity = workRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("Work", workConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchWorkByStatus(SearchData searchData) {
		try {
			List<WorkEntity> workEntities = new ArrayList<WorkEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<WorkEntity> pageTuts = workRepository.findStatus(paging);
			workEntities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse(ApplicationSettings.WORK, workConverter.toDTOs(workEntities),
					pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchWorkByEmployee(SearchData searchData) {
		Integer idProject = searchData.getIdProject();
		Integer idEmployee = searchData.getIdEmployee();
		try {
			List<WorkEntity> workEntities = new ArrayList<WorkEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<WorkEntity> pageTuts = idProject == null ? workRepository.getWorkByIdEmployee(idEmployee, paging)
					: workRepository.getByEmployee(idProject, idEmployee, paging);
			workEntities = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse(ApplicationSettings.WORK, workConverter.toDTOs(workEntities),
					pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

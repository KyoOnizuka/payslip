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

import com.wata.payslip.converter.WorkTimeConverter;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.WorkTimeDTO;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.WorkEntity;
import com.wata.payslip.model.entity.WorkTimeEntity;
import com.wata.payslip.repository.WorkTimeRepository;
import com.wata.payslip.service.Interface.IWorkTimeService;
import com.wata.payslip.utils.ApplicationSettings;
import com.wata.payslip.utils.PagingUtil;
import com.wata.payslip.utils.SortUtil;

@Service
public class WorkTimeService implements IWorkTimeService {
	@Autowired
	private WorkTimeRepository workTimeRepository;

	@Autowired
	private WorkTimeConverter workTimeConverter;

	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseEntity<Map<String, Object>> createWorkTime(WorkTimeDTO workTimeDTO) {
		WorkTimeEntity entity = workTimeConverter.toEntity(workTimeDTO);
		try {
			WorkEntity workEntity = entityManager.getReference(WorkEntity.class, workTimeDTO.getIdWork());
			entity.setWorkEntity(workEntity);
			EmployeeEntity employeeEntity = entityManager.getReference(EmployeeEntity.class,
					workTimeDTO.getIdEmployee());
			entity.setEmployeeEntity(employeeEntity);
			entity = workTimeRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("WorkTime", workTimeConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllWorkTime() {
		try {
			List<WorkTimeDTO> dtoes = workTimeConverter.toDTOs(workTimeRepository.findAll());
			Map<String, Object> response = new HashMap<>();
			response.put("WorkTimes", dtoes);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getWorkTimeById(Integer idWorktime) {
		try {
			WorkTimeEntity entity = workTimeRepository.findOneByIdWorktime(idWorktime);
			Map<String, Object> response = new HashMap<>();
			response.put("Work", workTimeConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteWorkTimeById(Integer idWorktime) {
		try {
			workTimeRepository.deleteById(idWorktime);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> updateWorkTimekById(WorkTimeDTO workTimeDTO, Integer idWorktime) {
		if (!(workTimeDTO.getIdWorktime() == idWorktime)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		if (workTimeRepository.findOneByIdWorktime(idWorktime) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		WorkTimeEntity entity = workTimeRepository.findOneByIdWorktime(idWorktime);
		entity = workTimeConverter.toEntity(workTimeDTO);
		entity.setIdWorktime(idWorktime);
		try {
			WorkEntity workEntity = entityManager.getReference(WorkEntity.class, workTimeDTO.getIdWork());
			entity.setWorkEntity(workEntity);
			EmployeeEntity employeeEntity = entityManager.getReference(EmployeeEntity.class,
					workTimeDTO.getIdEmployee());
			entity.setEmployeeEntity(employeeEntity);
			entity = workTimeRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("WorkTime", workTimeConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchWorkTimeBySummaryAndEmployee(SearchData searchData) {
		String value = searchData.getSearchValue().trim();
		try {
			List<WorkTimeEntity> workTimeEntities = new ArrayList<WorkTimeEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);

			Page<WorkTimeEntity> pageTuts;
			pageTuts = (value == null) ? workTimeRepository.findAll(paging)
					: workTimeRepository.findFull(value, value, value, paging);
			workTimeEntities = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse(ApplicationSettings.WORK,
					workTimeConverter.toDTOs(workTimeEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchWorkTimeByEmployee(SearchData searchData) {
		Integer idEmployee = searchData.getIdEmployee();
		Integer idProject = searchData.getIdProject();
		try {

			List<WorkTimeEntity> workTimeEntities = new ArrayList<WorkTimeEntity>();

			workTimeEntities = idProject == null ? workTimeRepository.getWorkTimeByWork(idEmployee)
					: workTimeRepository.getWorkTimeByEmployeeAndProject(idEmployee, idProject);
			Map<String, Object> response = new HashMap<>();
			response.put("WorkTime", workTimeConverter.toDTOs(workTimeEntities));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

package com.wata.payslip.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wata.payslip.model.dtos.EmployeeDTO;
import com.wata.payslip.model.dtos.ProjectDTO;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.ProjectEntity;
import com.wata.payslip.repository.EmployeeRepository;
import com.wata.payslip.service.Implements.EmployeeService;

@Component
public class ProjectConverter {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	EmployeeRepository employeeRepository;

	public ProjectEntity toEntity(ProjectDTO dto) {
		ProjectEntity entity = new ProjectEntity();
		entity.setNameProject(dto.getNameProject());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setDescription(dto.getDescription());
		entity.setStatus(dto.getStatus());
		Set<EmployeeEntity> employee = new HashSet<EmployeeEntity>();

		for (Integer user : dto.getIdEmployee()) {
			employee.add(employeeRepository.findById(user).get());
		}
		entity.setEmployee(employee);
		return entity;
	}

	public ProjectDTO toDTO(ProjectEntity entity) {
		ProjectDTO dto = new ProjectDTO();
		dto.setIdProject(entity.getIdProject());
		dto.setIdTypeProject(entity.getTypeProject().getId());
		dto.setNameProject(entity.getNameProject());
		dto.setStartDate(entity.getStartDate());
		dto.setEndDate(entity.getEndDate());
		dto.setDescription(entity.getDescription());
		dto.setStatus(entity.getStatus());
		dto.setTypeProject(entity.getTypeProject().getTypeName());
		List<Integer> idEmployees = new ArrayList<Integer>();
		for (EmployeeEntity user : entity.getEmployee()) {
			idEmployees.add(employeeService.convertToEmployeeDTO(user).getId());
		}
		dto.setIdEmployee(idEmployees);

		return dto;
	}

	public ProjectDTO toDTOEntity(ProjectEntity entity) {
		ProjectDTO dto = new ProjectDTO();
		dto.setIdProject(entity.getIdProject());
		dto.setIdTypeProject(entity.getTypeProject().getId());
		dto.setNameProject(entity.getNameProject());
		dto.setStartDate(entity.getStartDate());
		dto.setEndDate(entity.getEndDate());
		dto.setDescription(entity.getDescription());
		dto.setStatus(entity.getStatus());
		dto.setTypeProject(entity.getTypeProject().getTypeName());
		List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
		for (EmployeeEntity user : entity.getEmployee()) {
			employees.add(employeeService.convertToEmployeeDTO(user));
		}
		dto.setEmployee(employees);

		return dto;
	}

	public List<ProjectDTO> toDTOs(List<ProjectEntity> entities) {

		List<ProjectDTO> dtoes = new ArrayList<ProjectDTO>();
		for (ProjectEntity projectEntity : entities) {
			ProjectDTO dto = this.toDTO(projectEntity);
			dtoes.add(dto);

		}
		return dtoes;
	}

}

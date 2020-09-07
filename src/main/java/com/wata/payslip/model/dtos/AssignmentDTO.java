package com.wata.payslip.model.dtos;

import java.util.Set;

import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.ProjectEntity;

public class AssignmentDTO {

	private Integer idEmployee;
	private Integer idProject;
	private Set<EmployeeEntity> employee;
	private Set<ProjectEntity> project;

	public AssignmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public AssignmentDTO(Integer idProject, Integer idEmployee) {

		this.idProject = idProject;
		this.idEmployee = idEmployee;
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer projectEntity) {
		this.idProject = projectEntity;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer employeeEntity) {
		this.idEmployee = employeeEntity;
	}

	public Set<EmployeeEntity> getEmployee() {
		return employee;
	}

	public void setEmployee(Set<EmployeeEntity> set) {
		this.employee = set;
	}

	public Set<ProjectEntity> getProject() {
		return project;
	}

	public void setProject(Set<ProjectEntity> Set) {
		this.project = Set;
	}

}
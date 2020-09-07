package com.wata.payslip.model.dtos;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class ProjectDTO {
	private Integer idProject;
	@Pattern(regexp = "[a-zA-Z0-9 \\t\\n\\x0B\\f\\r\\p{M}\\p{L}]+")
	private String nameProject;
	@NotNull(message = "cannot be null")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date startDate;
	@NotNull(message = " cannot be null")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date endDate;
	private String description;
	@NotNull(message = " cannot be null")
	private Integer idTypeProject;
	private String status;
	private String typeProject;
	private List<EmployeeDTO> employee;
	private List<Integer> idEmployee;

	public ProjectDTO() {

	}

	public ProjectDTO(Integer idProject, String nameProject, Date startDate, Date endDate, String description,
			Integer idTypeProject, String status, List<EmployeeDTO> employee) {
		this.idProject = idProject;
		this.nameProject = nameProject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.idTypeProject = idTypeProject;
		this.status = status;
		this.employee = employee;
	}

	public Integer getIdTypeProject() {
		return idTypeProject;
	}

	public void setIdTypeProject(Integer idTypeProject) {
		this.idTypeProject = idTypeProject;
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(String typeProject) {
		this.typeProject = typeProject;
	}

	public List<EmployeeDTO> getEmployee() {
		return employee;
	}

	public void setEmployee(List<EmployeeDTO> employee) {
		this.employee = employee;
	}

	public List<Integer> getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(List<Integer> idEmployee) {
		this.idEmployee = idEmployee;
	}

}

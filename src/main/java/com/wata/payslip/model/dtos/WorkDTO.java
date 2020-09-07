package com.wata.payslip.model.dtos;

import javax.validation.constraints.Pattern;

public class WorkDTO {

	private Integer idWork;
	private Integer idProject;
	@Pattern(regexp = "[A-Za-z0-9 \\t\\n\\x0B\\f\\r\\p{L}\\.(@<!#%&*()>.,:?.)]+")
	private String summary;
	@Pattern(regexp = "[A-Za-z0-9 \\t\\n\\x0B\\f\\r\\p{L}\\.(@<!#%&*-_()>.,:?.)]+")
	private String description;
	private String status;
	private String nameProject;

	public WorkDTO() {

	}

	public WorkDTO(Integer idWork, Integer idProject, String summary, String description, String status,
			String nameProject) {
		super();
		this.idWork = idWork;
		this.idProject = idProject;
		this.summary = summary;
		this.description = description;
		this.status = status;
		this.nameProject = nameProject;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public Integer getIdWork() {
		return idWork;
	}

	public void setIdWork(Integer idWork) {
		this.idWork = idWork;
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

}

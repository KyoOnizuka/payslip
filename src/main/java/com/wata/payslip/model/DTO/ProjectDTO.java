package com.wata.payslip.model.DTO;

import java.util.Date;

import com.wata.payslip.model.entity.TypeProjectEntity;

public class ProjectDTO {

	private Integer id;

	// private List<AssignmentEntity> assigment = new ArrayList<>();

	private String nameProject;

	private Date startDate;

	public TypeProjectEntity typeProject;
	private Date endDate;

	private String description;

	public ProjectDTO() {

	}

	public ProjectDTO(Integer id, TypeProjectEntity typeProjectEntity, String nameProject, Date startDate, Date endDate,
			String description) {
		super();
		this.id = id;
		this.typeProject = typeProjectEntity;
		this.nameProject = nameProject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public TypeProjectEntity getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(TypeProjectEntity typeProjectEntity) {
		this.typeProject = typeProjectEntity;
	}

}
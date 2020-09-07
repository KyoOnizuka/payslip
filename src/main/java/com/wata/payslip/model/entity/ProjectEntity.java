package com.wata.payslip.model.entity;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Project")
public class ProjectEntity {

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "typeProject", nullable = false)
	public TypeProjectEntity typeProject;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "assignment", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private Set<EmployeeEntity> employee;

	@OneToMany(mappedBy = "projectEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<WorkEntity> workEntities;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdProject")
	private Integer idProject;

	@Column(name = "NameProject", columnDefinition = "nvarchar(255)")
	private String nameProject;

	@Column(name = "StartDate")
	private Date startDate;

	@Column(name = "EndDate")
	private Date endDate;

	@Column(name = "Description", columnDefinition = "nvarchar(255)")
	private String description;
	@Column(name = "status", columnDefinition = "nvarchar(255)")
	private String status;

	public ProjectEntity() {

	}

	public ProjectEntity(TypeProjectEntity typeProject, List<WorkEntity> workEntities, Integer idProject,
			String nameProject, Date startDate, Date endDate, String description, String status) {
		this.typeProject = typeProject;
		this.workEntities = workEntities;
		this.idProject = idProject;
		this.nameProject = nameProject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.status = status;

	}

	public TypeProjectEntity getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(TypeProjectEntity typeProject) {
		this.typeProject = typeProject;
	}

	public List<WorkEntity> getWorkEntities() {
		return workEntities;
	}

	public void setWorkEntities(List<WorkEntity> workEntities) {
		this.workEntities = workEntities;
	}

	public Set<EmployeeEntity> getEmployee() {
		return employee;
	}

	public void setEmployee(Set<EmployeeEntity> workEntities) {
		this.employee = workEntities;
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

}
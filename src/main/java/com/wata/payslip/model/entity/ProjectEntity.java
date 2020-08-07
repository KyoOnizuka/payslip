package com.wata.payslip.model.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
public class ProjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdProject")
	private Integer idProject;

//	@OneToMany(mappedBy = "projectEntity")
//	private List<TaskEntity> task = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "typeProject", nullable = false)
	public TypeProjectEntity typeProject;

	@OneToMany(mappedBy = "idProject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AssignmentEntity> assignmentEntity;

	@OneToMany(mappedBy = "projectEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WorkEntity> idWork;
	/*
	 * @OneToMany(mappedBy = "projectEntity") private List<AssignmentEntity>
	 * assigment = new ArrayList<>();
	 */
	@Column(name = "NameProject")
	// @Pattern(regexp = "[A-Za-z0-9 \\t\\n\\x0B\\f\\r\\p{L}]+")
	private String nameProject;

	@Column(name = "StartDate")
	private Date startDate;

	@Column(name = "EndDate")
	private Date endDate;

	@Column(name = "Description")
	private String description;

	public ProjectEntity() {

	}

	public ProjectEntity(Integer id, TypeProjectEntity typeProjectEntity, String nameProject, Date startDate,
			Date endDate, String description) {
		super();
		this.idProject = id;
//		this.task = task;
		this.typeProject = typeProjectEntity;
		this.nameProject = nameProject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer id) {
		this.idProject = id;
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
//
//	public List<TaskEntity> getTask() {
//		return task;
//	}
//
//	public void setTask(List<TaskEntity> task) {
//		this.task = task;
//	}

	public TypeProjectEntity getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(TypeProjectEntity typeProjectEntity) {
		this.typeProject = typeProjectEntity;
	}

}
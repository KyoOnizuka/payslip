package com.wata.payslip.model.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Work")
public class WorkEntity {

	@OneToMany(mappedBy = "workEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WorkTimeEntity> workTimeEntity;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "IdProject", nullable = false)
	private ProjectEntity projectEntity;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdWork")
	private Integer idWork;

	@Column(name = "Summary", nullable = false, columnDefinition = "nvarchar(255)")
	private String summary;

	@Column(name = "Description", columnDefinition = "nvarchar(255)")
	private String description;

	@Column(name = "Status")
	private String status;

	public WorkEntity() {

	}

	public WorkEntity(Set<WorkTimeEntity> workTimeEntity, ProjectEntity projectEntity, Integer idWork, String summary,
			String description, String status) {
		this.workTimeEntity = workTimeEntity;
		this.projectEntity = projectEntity;
		this.idWork = idWork;
		this.summary = summary;
		this.description = description;
		this.status = status;
	}

	public Set<WorkTimeEntity> getWorkTimeEntity() {
		return workTimeEntity;
	}

	public void setWorkTimeEntity(Set<WorkTimeEntity> workTimeEntity) {
		this.workTimeEntity = workTimeEntity;
	}

	public ProjectEntity getProjectEntity() {
		return projectEntity;
	}

	public void setProjectEntity(ProjectEntity projectEntity) {
		this.projectEntity = projectEntity;
	}

	public Integer getIdWork() {
		return idWork;
	}

	public void setIdWork(Integer idWork) {
		this.idWork = idWork;
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
package com.wata.payslip.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "workTime")
public class WorkTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdWorktime")
	private Integer idWorktime;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "IdEmployee", nullable = false)
	private EmployeeEntity employeeEntity;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "IdWork", nullable = false)
	private WorkEntity workEntity;

	@Column(name = "StartDate", nullable = false)
	private Date startDate;

	@Column(name = "Hour")
	private Double hour;

	@Column(name = "Title", columnDefinition = "nvarchar(255)")
	private String title;

	@Column(name = "Description", columnDefinition = "nvarchar(255)")
	private String description;

	public WorkTimeEntity() {

	}

	public WorkTimeEntity(Integer idWorktime, EmployeeEntity employeeEntity, WorkEntity workEntity, Date startDate,
			String title, String description) {
		this.idWorktime = idWorktime;
		this.employeeEntity = employeeEntity;
		this.workEntity = workEntity;
		this.startDate = startDate;
		this.title = title;
		this.description = description;

	}

	public Integer getIdWorktime() {
		return idWorktime;
	}

	public void setIdWorktime(Integer idWorktime) {
		this.idWorktime = idWorktime;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public WorkEntity getWorkEntity() {
		return workEntity;
	}

	public void setWorkEntity(WorkEntity workEntity) {
		this.workEntity = workEntity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

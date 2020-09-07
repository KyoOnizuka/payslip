package com.wata.payslip.model.dtos;

import java.sql.Date;

import javax.validation.constraints.Pattern;

public class WorkTimeDTO {
	private Integer idWorktime;
	private Integer idWork;
	private Integer idEmployee;
	private Date startDate;
	private Double hour;
	@Pattern(regexp = "[A-Za-z0-9 \\t\\n\\x0B\\f\\r\\p{L}\\.(@<!#%&*()>.,:?.)]+")
	private String title;
	@Pattern(regexp = "[A-Za-z0-9 \\t\\n\\x0B\\f\\r\\p{L}\\.(@<!#%&*()>.,:?.)]+")
	private String description;
	private String fullName;
	private String summary;

	public WorkTimeDTO() {

	}

	public WorkTimeDTO(Integer idWorktime, Integer idWork, Integer idEmployee, Date startDate, Double hour,
			String title, String description, String fullName, String summary) {
		this.idWorktime = idWorktime;
		this.idWork = idWork;
		this.idEmployee = idEmployee;
		this.startDate = startDate;
		this.hour = hour;
		this.title = title;
		this.description = description;
		this.fullName = fullName;
		this.summary = summary;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getIdWorktime() {
		return idWorktime;
	}

	public void setIdWorktime(Integer idWorktime) {
		this.idWorktime = idWorktime;
	}

	public Integer getIdWork() {
		return idWork;
	}

	public void setIdWork(Integer idWork) {
		this.idWork = idWork;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
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

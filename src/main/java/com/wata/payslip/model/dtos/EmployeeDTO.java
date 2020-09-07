package com.wata.payslip.model.dtos;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.wata.payslip.model.entity.AccountEntity;

public class EmployeeDTO {
	private Integer id;
	@Pattern(regexp = "[a-zA-Z \\t\\n\\x0B\\f\\r\\p{M}\\p{L}]+")
	private String fullName;

	@Email(regexp = ".+@.+\\..+", message = "Please enter a valid e-mail address")
	private String email;

	@Size(min = 10, max = 11)
	@Pattern(regexp = "[0-9]+")
	private String telephone;

	// @DateTimeFormat(pattern = "yyyy-mm-dd")
	// @Past(message = "Nam sinh khong duoc lon hon nam hien tai")
	private Date birthday;
	// @NotNull(message = "Name cannot be null")
	// @DateTimeFormat(pattern = "yyyy-mm-dd")
	// @Past(message = "Nam sinh khong duoc lon hon nam hien tai")
	private Date joinDay;
	private AccountEntity employeeAccount;
	private Integer accountId;
	private String status;
	private List<ProjectDTO> project;
	private Double grossSalary;

	public EmployeeDTO() {

	}

	public EmployeeDTO(String name, String tel, Date birthdate, Date joindate, List<ProjectDTO> project,
			Double grossSalary) {

		this.fullName = name;
		this.telephone = tel;
		this.birthday = birthdate;
		this.joinDay = joindate;
		this.project = project;
		this.grossSalary = grossSalary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String lastName) {
		this.fullName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String tel) {
		this.telephone = tel;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthdate) {
		this.birthday = birthdate;
	}

	public Date getJoinDay() {
		return joinDay;
	}

	public void setJoinDay(Date joindate) {
		this.joinDay = joindate;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProjectDTO> getProject() {
		return project;
	}

	public void setProject(List<ProjectDTO> project) {
		this.project = project;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

}

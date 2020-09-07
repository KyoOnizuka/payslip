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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class EmployeeEntity {
	private Integer id;

	private String fullName;
	private String telephone;
	private Date birthday;
	private Date joinDay;
	private String status;

	@OneToMany(mappedBy = "employeeEntity")
	private Set<WorkTimeEntity> workTimeEtity;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AccountEntity account;

	@ManyToMany(mappedBy = "employee", cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Set<ProjectEntity> project;

	@OneToMany(mappedBy = "employeeEntity")
	private List<PayslipEntity> payslipEntity;
	@OneToMany(mappedBy = "idEmployee")
	private Set<FileManagerEntity> idFile;
	@Column(name = "GrossSalary")
	private Double grossSalary;

	public EmployeeEntity() {

	}

	public EmployeeEntity(Integer id, String name, String tel, Date birthdate, Date joindate, Double grossSalary) {
		this.id = id;
		this.fullName = name;
		this.telephone = tel;
		this.birthday = birthdate;
		this.joinDay = joindate;
		this.grossSalary = grossSalary;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "fullname", nullable = false, columnDefinition = "nvarchar(255)")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String lastName) {
		this.fullName = lastName;
	}

	@ManyToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	public Set<ProjectEntity> getProjectEntity() {
		return project;
	}

	public void setProjectEntity(Set<ProjectEntity> workEntities) {
		this.project = workEntities;
	}

	@Column(name = "telephone", nullable = false)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String string) {
		this.telephone = string;
	}

	@Column(name = "birthday", nullable = false)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthdate) {
		this.birthday = birthdate;
	}

	@Column(name = "joinDay", nullable = false)
	public Date getJoinDay() {
		return joinDay;
	}

	public void setJoinDay(Date joindate) {
		this.joinDay = joindate;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity birthdate) {
		this.account = birthdate;
	}

	@Column(name = "status", columnDefinition = "nvarchar(255)")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "idEmployee")
	public Set<FileManagerEntity> getFile() {
		return idFile;
	}

	public void setFile(Set<FileManagerEntity> file) {
		this.idFile = file;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

}

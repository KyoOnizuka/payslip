package com.wata.payslip.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "useraccount")
public class AccountEntity {

	private int id;
	@Column(name = "email", nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "active", nullable = false)
	private Boolean active = false;

	@Column(name = "roles", nullable = false)
	private String roles = "ROLE_USER";

	@Column(name = "token", nullable = false)
	private String token;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private EmployeeEntity employeeEntity;

	public AccountEntity() {

	}

	public AccountEntity(String email, String password, String token, String roles) {

		this.username = email;
		this.password = password;
		this.token = token;
		this.roles = roles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "email", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity token) {
		this.employeeEntity = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

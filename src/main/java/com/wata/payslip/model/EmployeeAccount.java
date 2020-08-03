package com.wata.payslip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "greeting")
public class EmployeeAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	@Column(name = "email", nullable = false)
	private String userName;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "active", nullable = false)
	private Boolean active = false;

	@Column(name = "roles", nullable = false)
	private String roles = "ROLE_USER";

	@Column(name = "token", nullable = false)
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "email", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "active", nullable = false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(name = "roles", nullable = false)
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Column(name = "token", nullable = false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

package com.wata.payslip.model.dtos;

import com.wata.payslip.utils.Roles;

public class AccountDTO {

	private int id;
	private String username;
	private String password;
	private Boolean active = false;
	private String roles = Roles.ROLE_USER;
	private String token;

	public AccountDTO() {

	}

	public AccountDTO(int id, String email, String password, String token, String roles) {
		this.id = id;
		this.username = email;
		this.password = password;
		this.token = token;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}

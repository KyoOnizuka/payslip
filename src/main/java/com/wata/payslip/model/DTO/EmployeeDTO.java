package com.wata.payslip.model.DTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

public class EmployeeDTO {

	@Pattern(regexp = "[a-zA-Z \\t\\n\\x0B\\f\\r\\p{M}\\p{L}]+")
	private String fullName;

	@Email(regexp = ".+@.+\\..+", message = "Please enter a valid e-mail address")
	private String email;

	@Size(min = 10, max = 11)
	@Pattern(regexp = "[0-9]+")
	private String telephone;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Past(message = "Nam sinh khong duoc lon hon nam hien tai")
	private Date birthday;
	// @NotNull(message = "Name cannot be null")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Past(message = "Nam sinh khong duoc lon hon nam hien tai")
	private Date joinDay;

	private String token;
	private String password;
	private List<GrantedAuthority> authorities;

	public EmployeeDTO() {

	}

	public EmployeeDTO(String name, String tel, Date birthdate, Date joindate) {

		this.fullName = name;

		this.telephone = tel;
		this.birthday = birthdate;
		this.joinDay = joindate;

	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String lastName) {
		this.fullName = lastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String tel) {
		this.telephone = tel;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthdate) {
		this.birthday = birthdate;
	}

	public java.util.Date getJoinDay() {
		return joinDay;
	}

	public void setJoinDay(java.util.Date joindate) {
		this.joinDay = joindate;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToken() {
		// TODO Auto-generated method stub
		return token;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	public void setToken(String token2) {
		// TODO Auto-generated method stub

	}

	/*
	 * public Boolean getActivated() { return activated; }
	 * 
	 * public void setActivated(Boolean activated) { this.activated = activated; }
	 */

}

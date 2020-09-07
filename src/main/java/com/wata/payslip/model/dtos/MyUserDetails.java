package com.wata.payslip.model.dtos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wata.payslip.model.entity.AccountEntity;

public class MyUserDetails implements UserDetails {

	private String username;
	private String email;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;

	public MyUserDetails() {

	}

	public MyUserDetails(AccountEntity user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	public void setEmail(String email) {
		// TODO Auto-generated method stub
		this.email = email;
	}

}

package com.wata.payslip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wata.payslip.model.EmployeeAccount;

public interface AuthRepository extends JpaRepository<EmployeeAccount, Long> {
	public Optional<EmployeeAccount> findByUserName(String string);

	public EmployeeAccount findByTokenAndUserName(String string, String string2);

	// public EmployeeAccount findByAndUserName(String string, String string2);

}

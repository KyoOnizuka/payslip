package com.wata.payslip.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wata.payslip.employeeDTO.MyUserDetails;
import com.wata.payslip.model.EmployeeAccount;
import com.wata.payslip.repository.AuthRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	AuthRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<EmployeeAccount> user = userRepository.findByUserName(userName);

		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

		return user.map(MyUserDetails::new).get();
	}
}

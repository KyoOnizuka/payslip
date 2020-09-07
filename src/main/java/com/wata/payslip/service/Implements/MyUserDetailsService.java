package com.wata.payslip.service.Implements;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wata.payslip.model.dtos.MyUserDetails;
import com.wata.payslip.model.entity.AccountEntity;
import com.wata.payslip.repository.AccountRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	AccountRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<AccountEntity> user = userRepository.findByUsername(userName);

		return user.map(MyUserDetails::new).get();
	}
}

package com.wata.payslip.controller;

import java.util.Map;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.filter.AuthenticationResponse;
import com.wata.payslip.filter.JwtUtil;
import com.wata.payslip.model.DTO.EmployeeDTO;
import com.wata.payslip.model.DTO.MyUserDetails;
import com.wata.payslip.model.DTO.SearchData;
import com.wata.payslip.service.EmployeeService;
import com.wata.payslip.service.MyUserDetailsService;

@RestController
@RequestMapping("/api/general")
public class GeneralController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private MyUserDetailsService userDetailsService;

	// Active account after Register
	@PutMapping("/active")
	public ResponseEntity<?> activatedEmployee(@RequestBody EmployeeDTO token) throws Exception {
		return employeeService.activation(token);
	}

	@RequestMapping(value = "/login", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody MyUserDetails authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@RequestMapping(value = "/resetpassword", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> resetPassword(@RequestBody EmployeeDTO employeeDTO) throws RelationNotFoundException {

		return employeeService.MailReset(this.emailSender, employeeDTO);
	}

	@RequestMapping(value = "/verify", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> verifyToken(@RequestBody EmployeeDTO token) throws Exception {
		return employeeService.verifyToken(token);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(@RequestBody SearchData searchData) {
		// default currentPage = 0, pageSize = 3
		return employeeService.searchEmployeeByFullName(searchData);
	}

	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> createNguoiDung(@Validated @RequestBody EmployeeDTO nguoiDung)
			throws RelationNotFoundException {
		String token = employeeService.sendMail(this.emailSender, nguoiDung);
		return employeeService.createNguoiDung(nguoiDung, token);
	}

}

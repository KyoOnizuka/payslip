package com.wata.payslip.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.employeeDTO.AuthenticationResponse;
import com.wata.payslip.employeeDTO.EmployeeDTO;
import com.wata.payslip.employeeDTO.MyUserDetails;
import com.wata.payslip.employeeDTO.SearchData;
import com.wata.payslip.filter.JwtUtil;
import com.wata.payslip.service.Mapservice;
import com.wata.payslip.service.MyUserDetailsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class GreetingController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private Mapservice mapService;
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	private MyUserDetailsService userDetailsService;

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	// Get all employees info
	@GetMapping("/employee")
	public List<EmployeeDTO> findAll() {
		return mapService.getAll();
	}

	// Get employee info base on id
	@GetMapping("/employee/{id}")
	public Optional<EmployeeDTO> getGreetingById(@PathVariable("id") Long Id) {
		return mapService.getById(Id);
	}

	// Insert employee into database
	@RequestMapping(value = "/employee", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> createNguoiDung(@Validated @RequestBody EmployeeDTO nguoiDung)
			throws RelationNotFoundException {
		String token = mapService.sendMail(this.emailSender, nguoiDung);
		return mapService.createNguoiDung(nguoiDung, token);
	}

	// Delete employee base on id info
	@DeleteMapping("/employee/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws RelationNotFoundException {
		return mapService.deleteEmployee(employeeId);
	}

	// Update employee info base on id
	@PutMapping("/employee/{id}")
	public Map<String, Boolean> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody EmployeeDTO employeeDetails) throws RelationNotFoundException {
		return mapService.updates(employeeDetails, employeeId);
	}

	// Active account after Register
	@PutMapping("/employee/active")
	public Map<String, Boolean> activatedEmployee(@RequestBody EmployeeDTO token) throws Exception {
		return mapService.activation(token);
	}

	@RequestMapping(value = "/authenticate", headers = "Accept=application/json", method = RequestMethod.POST)
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

	@GetMapping("/employee/test")
	public String admin() {
		return ("<h1>Welcome Admin</h1>");
	}

	@RequestMapping(value = "/employee/pages", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(@RequestBody SearchData searchData) {
		// default currentPage = 0, pageSize = 3
		return mapService.searchEmployeeByFullName(searchData);
	}
}

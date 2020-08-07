package com.wata.payslip.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.model.DTO.EmployeeDTO;
import com.wata.payslip.model.DTO.SearchData;
import com.wata.payslip.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	public JavaMailSender emailSender;

	// Get all employees info
	@GetMapping("/")
	public List<EmployeeDTO> findAll() {
		return employeeService.getAll();
	}

	// Get employee info base on id
	@GetMapping("/{id}")
	public Optional<EmployeeDTO> getGreetingById(@PathVariable("id") int Id) {
		return employeeService.getById(Id);
	}

	// Insert employee into database

	// Delete employee base on id info
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") int employeeId)
			throws RelationNotFoundException {
		return employeeService.deleteEmployee(employeeId);
	}

	// Update employee info base on id
	@PutMapping("/{id}")
	public Map<String, Boolean> updateEmployee(@PathVariable(value = "id") int employeeId,
			@Validated @RequestBody EmployeeDTO employeeDetails) throws RelationNotFoundException {
		return employeeService.updates(employeeDetails, employeeId);
	}

	/*
	 * @RequestMapping(value = "/logout", headers = "Accept=application/json",
	 * method = RequestMethod.POST) public ResponseEntity<String>
	 * logoutNguoiDung(@RequestHeader(name = "Authorization") String jwt) throws
	 * RelationNotFoundException { String token = jwt.substring(7); return
	 * employeeService.logout(token); }
	 */
	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> createNguoiDung(@Validated @RequestBody EmployeeDTO nguoiDung)
			throws RelationNotFoundException {
		String token = employeeService.sendMail(this.emailSender, nguoiDung);
		return employeeService.createNguoiDung(nguoiDung, token);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(@RequestBody SearchData searchData) {
		// default currentPage = 0, pageSize = 3
		return employeeService.searchEmployeeByFullName(searchData);
	}

	@RequestMapping(value = "/logout", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> logoutNguoiDung(@RequestHeader(name = "Authorization") String jwt)
			throws RelationNotFoundException {
		String token = jwt.substring(7);
		return employeeService.logout(token);
	}

}

package com.wata.payslip.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.model.dtos.EmployeeDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.service.Interface.IEmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	@Autowired
	public HttpServletRequest request;
	@Autowired
	public IEmployeeService iEmployeeService;
	@Autowired
	public JavaMailSender emailSender;

	@GetMapping("/")
	public List<EmployeeDTO> findAll() {

		return iEmployeeService.getAll();
	}

	@GetMapping("/search")
	public List<EmployeeDTO> getGreetingById(@RequestParam("fullName") String fullName) {
		return iEmployeeService.getByFullName(fullName);
	}

	@GetMapping("/{id}")
	public Optional<EmployeeDTO> getGreetingById(@PathVariable("id") int Id) {
		return iEmployeeService.getById(Id);
	}

	/*------------------------------Get by email------------------------------*/
	@RequestMapping(value = "/email", headers = "Accept=application/json", method = RequestMethod.POST)
	public EmployeeDTO getGreetingEmail(@RequestBody String email) {
		return iEmployeeService.getByEmail(email);
	}

	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> createNguoiDung(@Validated @RequestBody EmployeeDTO nguoiDung)
			throws RelationNotFoundException {

		String token = iEmployeeService.sendMail(this.emailSender, nguoiDung);
		return iEmployeeService.createNguoiDung(nguoiDung, token);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Integer id) throws RelationNotFoundException {
		return iEmployeeService.deleteEmployee(id);
	}

	// Update employee info base on id
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@Validated @RequestBody EmployeeDTO employeeDetails,
			@PathVariable(value = "id") Integer employeeId) throws RelationNotFoundException {
		return iEmployeeService.updates(employeeDetails, employeeId);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(@RequestBody SearchData searchData) {
		return iEmployeeService.searchEmployeeByFullName(searchData);
	}

	@RequestMapping(value = "/avatar/{email}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAvatar(@PathVariable("email") String email) throws IOException {
		return iEmployeeService.getImage(email);

	}
}

package com.wata.payslip.service.Interface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import com.wata.payslip.model.dtos.EmployeeDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.entity.EmployeeEntity;

public interface IEmployeeService {

	// Get List of Employee
	public List<EmployeeDTO> getAll();

	// Convert Entity to DTO
	public EmployeeDTO convertToEmployeeDTO(EmployeeEntity user);

	// Get a Employee Info
	public Optional<EmployeeDTO> getById(int id);

	public List<EmployeeDTO> getByFullName(String id);

	// Delete a Employee
	public ResponseEntity<Map<String, Object>> deleteEmployee(Integer id) throws RelationNotFoundException;

	// Create a Account of Employee

	public ResponseEntity<Map<String, Object>> createNguoiDung(EmployeeDTO nguoiDung, String token)
			throws RelationNotFoundException;

	// Updates Account Info
	public ResponseEntity<Map<String, Object>> updates(EmployeeDTO user, Integer employeeId)
			throws RelationNotFoundException;

	// SendEmail
	public String sendMail(JavaMailSender emailSender, EmployeeDTO email);

	public ResponseEntity<Map<String, Object>> MailReset(JavaMailSender emailSender, EmployeeDTO email);

	// Random account verify code
	public String getRandomNumberInts(int min, int max);

	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(SearchData searchData);

	public EmployeeDTO getByEmail(String email);

	public ResponseEntity<?> getImage(String email);

}

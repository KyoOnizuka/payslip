package com.wata.payslip.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import javax.mail.MessagingException;

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
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.wata.payslip.model.dtos.PayslipDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.service.Interface.IPayslipService;

@RestController
@RequestMapping("/api/payslip")
public class PayslipController {
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	private IPayslipService iPayslipService;

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getPayslipById(@PathVariable(value = "id") Integer idPayslip) {
		return iPayslipService.getPayslipById(idPayslip);
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllPayslip() {
		return iPayslipService.getAllPayslip();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deletePayslipById(@PathVariable(value = "id") Integer idPayslip) {
		return iPayslipService.deletePayslipById(idPayslip);
	}

	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createPayslip(@Validated @RequestBody PayslipDTO payslipDTO) {
		return iPayslipService.createPayslip(payslipDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updatePayslipById(@RequestBody PayslipDTO payslipDTO,
			@PathVariable Integer id) {
		return iPayslipService.updatePayslipById(payslipDTO, id);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchPayslipByIdEmployee(@RequestBody SearchData searchData) {
		return iPayslipService.searchPayslipByIdEmployee(searchData);
	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchPayslipByStatus(@RequestBody SearchData searchData) {
		return iPayslipService.searchPayslipByStatus(searchData);
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchPayslipByEmail(@RequestBody SearchData searchData) {
		return iPayslipService.searchPayslipByEmail(searchData);
	}

	@RequestMapping(value = "/month", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchPayslipByMonth(@RequestBody SearchData searchData) {
		return iPayslipService.searchPayslipByMonth(searchData);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getPages(@RequestBody SearchData searchData) {
		return iPayslipService.getPage(searchData);
	}

	@RequestMapping(value = "/sendonereport", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> sendMailToEmployee(@RequestBody PayslipDTO payslipDTO)
			throws DocumentException, MessagingException, IOException {
		return iPayslipService.sendMailToEmployee(emailSender, payslipDTO);
	}

	@RequestMapping(value = "/sendall", headers = "Accept= application/json", method = RequestMethod.POST)
	public ResponseEntity<?> sendAll(@RequestBody Date searchData) {
		return iPayslipService.sendAll(searchData);
	}

}

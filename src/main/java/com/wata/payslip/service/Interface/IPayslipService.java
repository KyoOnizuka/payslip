package com.wata.payslip.service.Interface;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import com.itextpdf.text.DocumentException;
import com.wata.payslip.model.dtos.PayslipDTO;
import com.wata.payslip.model.dtos.SearchData;

public interface IPayslipService {
	public ResponseEntity<Map<String, Object>> createPayslip(PayslipDTO payslipDTO);

	public ResponseEntity<Map<String, Object>> getAllPayslip();

	public ResponseEntity<Map<String, Object>> getPayslipById(Integer idPayslip);

	public ResponseEntity<Map<String, Object>> deletePayslipById(Integer idPayslip);

	public ResponseEntity<Map<String, Object>> updatePayslipById(PayslipDTO payslipDTO, Integer idPayslip);

	public ResponseEntity<Map<String, Object>> searchPayslipByIdEmployee(SearchData searchData);

	public ResponseEntity<Map<String, Object>> searchPayslipByStatus(SearchData searchData);

	public ResponseEntity<Map<String, Object>> searchPayslipByEmail(SearchData searchData);

	public ResponseEntity<Map<String, Object>> searchPayslipByMonth(SearchData searchData);

	public ResponseEntity<Map<String, Object>> getPage(SearchData searchData);

	public ResponseEntity<?> sendMailToEmployee(JavaMailSender emailSender, PayslipDTO payslipDTO)
			throws DocumentException, MessagingException, IOException;

	public ResponseEntity<?> sendAll(Date searchData);
}

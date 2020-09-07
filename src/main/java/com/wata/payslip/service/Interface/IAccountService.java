package com.wata.payslip.service.Interface;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.management.relation.RelationNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import com.wata.payslip.model.dtos.AccountDTO;
import com.wata.payslip.model.dtos.MyUserDetails;

public interface IAccountService {

	public ResponseEntity<?> activation(AccountDTO token) throws Exception;

	public ResponseEntity<Map<String, Object>> verifyToken(String token) throws Exception;

	public ResponseEntity<Map<String, Object>> MailReset(JavaMailSender emailSender, AccountDTO email);

	public ResponseEntity<?> login(MyUserDetails authenticationRequest) throws Exception;

	public ResponseEntity<?> verifyPassword(String password, String token) throws RelationNotFoundException;

	public ResponseEntity<?> changePassword(String password, String token) throws RelationNotFoundException;

	public String getMD5(String data) throws NoSuchAlgorithmException;

	public ResponseEntity<?> setRole(String email, String role);
}

package com.wata.payslip.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.model.dtos.AccountDTO;
import com.wata.payslip.model.dtos.MyUserDetails;
import com.wata.payslip.service.Interface.IAccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private IAccountService iAccountService;

	@Autowired
	public JavaMailSender emailSender;

//------------------------Xác nhận token được gửi qua mail
	@RequestMapping(value = "/verify", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> verifyToken(@RequestBody String token) throws Exception {
		return iAccountService.verifyToken(token);
	}

	// ---------- Kích hoạt tài khoản----------
	@RequestMapping(value = "/active", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> activatedEmployee(@RequestBody AccountDTO token) throws Exception {
		return iAccountService.activation(token);
	}

	@RequestMapping(value = "/forgot", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody AccountDTO accountDTO)
			throws RelationNotFoundException {

		return iAccountService.MailReset(this.emailSender, accountDTO);
	}

	@RequestMapping(value = "/login", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody MyUserDetails authenticationRequest)
			throws Exception {
		return iAccountService.login(authenticationRequest);

	}

	@RequestMapping(value = "/successlogout", headers = "Accept=application/json", method = RequestMethod.GET)
	public ResponseEntity<?> logoutNguoiDung() {

		return new ResponseEntity<>(HttpStatus.OK);
	}

//------------------------------1. Xác nhận password cũ ----------------
	@RequestMapping(value = "/verifypassword", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> verifyPassword(@RequestBody String password,
			@RequestHeader(name = "Authorization") String jwt) throws RelationNotFoundException {
		return iAccountService.verifyPassword(password, jwt);
	}

	// --------------------------------Cập nhật password mới
	// ---------------------------
	@RequestMapping(value = "/changepassword", headers = "Accept=application/json", method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword(@RequestHeader(name = "Authorization") String token,
			@RequestBody String password) throws RelationNotFoundException {
		return iAccountService.changePassword(password, token);
	}

	@RequestMapping(value = "/test", headers = "Accept=application/json", method = RequestMethod.POST)
	public String changePassword(@RequestBody String password) throws RelationNotFoundException {
		try {
			return iAccountService.getMD5(password);
		} catch (NoSuchAlgorithmException e) {
			return password;
		}
	}

	@PutMapping(value = "/role/{email}")
	public ResponseEntity<?> setRole(@RequestBody String role, @PathVariable("email") String email) {
		return iAccountService.setRole(email, role);
	}
}

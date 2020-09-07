package com.wata.payslip.service.Implements;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.management.relation.RelationNotFoundException;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wata.payslip.filter.AuthenticationResponse;
import com.wata.payslip.filter.JwtUtil;
import com.wata.payslip.model.dtos.AccountDTO;
import com.wata.payslip.model.dtos.MyUserDetails;
import com.wata.payslip.model.entity.AccountEntity;
import com.wata.payslip.repository.AccountRepository;
import com.wata.payslip.service.Interface.IAccountService;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;

	// Create a Account of Employee

	@Override
	public ResponseEntity<?> activation(AccountDTO token) throws Exception {

		String encryptPass = getMD5(token.getPassword());
		Map<String, Boolean> response = new HashMap<>();
		try {
			AccountEntity employeeAccount = accountRepository.findByToken(token.getToken());
			if (employeeAccount != null) {
				employeeAccount.setActive(true);
				employeeAccount.setToken(null);
				employeeAccount.setPassword(encryptPass);

				response.put("success", Boolean.TRUE);
				accountRepository.save(employeeAccount);

				final UserDetails userDetails = userDetailsService.loadUserByUsername(employeeAccount.getUsername());

				final String jwt = jwtTokenUtil.generateToken(userDetails);

				return ResponseEntity.ok(new AuthenticationResponse(jwt));

			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> verifyToken(String token) throws Exception {
		try {
			if (accountRepository.findByToken(token) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> MailReset(JavaMailSender emailSender, AccountDTO email) {
		SimpleMailMessage message = new SimpleMailMessage();

		String random = employeeService.getRandomNumberInts(1, 100);
		try {
			AccountEntity user = accountRepository.myCustomQuery2(email.getUsername());
			if (user != null) {
				if (user.isActive() == false)
					return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				else {
					message.setTo(email.getUsername());
					message.setSubject("Test Simple Email");
					message.setText(" Your verify Token: " + "\n token=" + random); // Send
					emailSender.send(message);
					user.setToken(random);
					accountRepository.save(user);
					return new ResponseEntity<>(HttpStatus.OK);

				}
			} else {

				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> login(MyUserDetails authenticationRequest) throws Exception {
		try {

			String encryptPass = getMD5(authenticationRequest.getPassword());
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), encryptPass));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		AccountEntity account = accountRepository.findByUsername(authenticationRequest.getUsername()).get();
		AccountDTO accountdto = new AccountDTO();
		account.setToken(jwt);
		account.setEmployeeEntity(null);
		BeanUtils.copyProperties(account, accountdto);
		accountdto.setPassword(null);
		return ResponseEntity.ok(accountdto);
	}

	@Override
	public ResponseEntity<?> verifyPassword(String password, String token) throws RelationNotFoundException {

		try {
			String username = jwtUtil.extractUsername(token.substring(7));
			String encryptPass = getMD5(password);
			if (accountRepository.findByPasswordAndUsername(encryptPass, username) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> changePassword(String password, String token) throws RelationNotFoundException {
		try {
			String username = jwtUtil.extractUsername(token.substring(7));
			String encryptPass = getMD5(password);
			AccountEntity account = accountRepository.findByUsername(username).get();
			account.setPassword(encryptPass);
			accountRepository.save(account);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public String getMD5(String data) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		messageDigest.update(data.getBytes());
		byte[] digest = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash.toString();
	}

	@Override
	public ResponseEntity<?> setRole(String email, String role) {

		AccountEntity account = accountRepository.findByUsername(email).get();
		account.setRoles(role);
		accountRepository.save(account);
		return new ResponseEntity(null, HttpStatus.OK);
	}

}

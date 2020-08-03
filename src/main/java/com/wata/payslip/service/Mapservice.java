package com.wata.payslip.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wata.payslip.employeeDTO.EmployeeDTO;
import com.wata.payslip.employeeDTO.SearchData;
import com.wata.payslip.model.EmployeeAccount;
import com.wata.payslip.model.EmployeeEntity;
import com.wata.payslip.repository.AuthRepository;
import com.wata.payslip.repository.EmployeeRepository;

@Service
public class Mapservice {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AuthRepository authRepository;

	// Get List of Employee
	public List<EmployeeDTO> getAll() {
		return ((List<EmployeeEntity>) employeeRepository.findAll()).stream().map(this::convertToEmployeeDTO)
				.collect(Collectors.toList());
	}

	// Convert Entity to DTO
	private EmployeeDTO convertToEmployeeDTO(EmployeeEntity user) {
		EmployeeDTO userLocationDTO = new EmployeeDTO();
		userLocationDTO.setFullName(user.getFullName());
		userLocationDTO.setEmail(user.getEmail());
		userLocationDTO.setTelephone(user.getTelephone());
		userLocationDTO.setBirthday(user.getBirthday());
		userLocationDTO.setJoinDay(user.getJoinDay());
		return userLocationDTO;
	}

	// Get a Employee Info
	public Optional<EmployeeDTO> getById(Long id) {
		return (employeeRepository.findById(id)).map(this::convertToEmployeeDTO);
	}

	public ResponseEntity<String> login(EmployeeDTO usernameAndPassword) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Delete a Employee
	public Map<String, Boolean> deleteEmployee(Long id) throws RelationNotFoundException {
		Optional<EmployeeEntity> employee = employeeRepository.findById(id);
		Map<String, Boolean> response = new HashMap<>();
		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("Wrong id", Boolean.FALSE);
		}
		return response;
	}

	// Create a Account of Employee
	public ResponseEntity<String> createNguoiDung(EmployeeDTO nguoiDung, String token)
			throws RelationNotFoundException {
		nguoiDung.setToken(token);
		EmployeeEntity userEntity = new EmployeeEntity();
		EmployeeAccount account = new EmployeeAccount();
		BeanUtils.copyProperties(nguoiDung, account);
		account.setUserName(nguoiDung.getEmail());
		authRepository.save(account);
		BeanUtils.copyProperties(nguoiDung, userEntity);
		employeeRepository.save(userEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Updates Account Info
	public Map<String, Boolean> updates(EmployeeDTO user, long id) throws RelationNotFoundException {
		EmployeeEntity employee = employeeRepository.findById(id)
				.orElseThrow(() -> new RelationNotFoundException("Employee not found for this id :: " + id));

		if (user.getFullName() != null) {
			employee.setFullName(user.getFullName());
		}

		if (user.getEmail() != null) {
			employee.setEmail(user.getEmail());
		}

		if (user.getTelephone() != null) {
			employee.setTelephone(user.getTelephone());
		}

		if (user.getBirthday() != null) {
			employee.setBirthday(user.getBirthday());
		}

		if (user.getJoinDay() != null) {
			employee.setJoinDay(user.getJoinDay());
		}

		final EmployeeEntity updatedEmployee = employeeRepository.save(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("success", Boolean.TRUE);
		return response;
	}

	// Verify Account
	public Map<String, Boolean> activation(EmployeeDTO token) throws Exception {
		// EmployeeAccount employee =
		// authRepository.findByTokenAndUserName(token.getToken(), token.getEmail());
		/*
		 * try { authenticationManager .authenticate(new
		 * UsernamePasswordAuthenticationToken(token.getToken(), token.getEmail())); }
		 * catch (BadCredentialsException e) { throw new
		 * Exception("Incorrect username or password", e); }
		 */
		Map<String, Boolean> response = new HashMap<>();
		if (authRepository.findByTokenAndUserName(token.getToken(), token.getEmail()) != null) {
			authRepository.findByTokenAndUserName(token.getToken(), token.getEmail()).setActive(true);
			authRepository.findByTokenAndUserName(token.getToken(), token.getEmail()).setPassword(token.getPassword());
			response.put("success", Boolean.TRUE);
			final EmployeeAccount updatedEmployee = authRepository
					.save(authRepository.findByTokenAndUserName(token.getToken(), token.getEmail()));
			response.put("success", Boolean.TRUE);
		} else {
			response.put("success", Boolean.FALSE);
		}
		return response;
	}

	// SendEmail
	public String sendMail(JavaMailSender emailSender, EmployeeDTO email) {
		SimpleMailMessage message = new SimpleMailMessage();

		String random = getRandomNumberInts(1, 100);

		message.setTo(email.getEmail());
		message.setSubject("Test Simple Email");
		message.setText("Welcome to our Website. Thankyou for Register \n This is your Token: "
				+ "\n https://www.google.com/search?token=" + random + "&username=" + email.getEmail()); // Send
		// Message!
		emailSender.send(message);
		return random;
	}

	// Random account verify code
	public String getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return Long.toString(random.ints(min, (max + 1)).findFirst().getAsInt());
	}

	public List<EmployeeEntity> createSearchEmployee(EmployeeDTO employee) {
		ArrayList<EmployeeEntity> result = new ArrayList<EmployeeEntity>();
		ArrayList<EmployeeEntity> data = (ArrayList<EmployeeEntity>) employeeRepository.findAll();
		ArrayList<EmployeeEntity> tmp = new ArrayList<EmployeeEntity>();

		if (employee.getEmail() != null) {
			result.clear();
			for (EmployeeEntity employeeEntity : data) {
				if (employeeEntity.getEmail() == employee.getEmail()) {
					result.add(employeeEntity);
					return result;
				}
			}
		}

		if (employee.getFullName() == null) {
			employee.setFullName("");
		}

		if (employee.getTelephone() == null) {
			employee.setTelephone("");
		}

		if (employee.getBirthday() == null) {
			employee.setBirthday(new Date(0));
		}

		if (employee.getJoinDay() == null) {
			employee.setJoinDay(new Date(0));
		}

//		for (EmployeeEntity employeeEntity : data) {
//			if (employeeEntity.getFullName().toLowerCase().contains(employee.))
//		}

		return result;
	}

	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(SearchData searchData) {
		String fullName = searchData.getSearchValue();
		Integer currentPage, pageSize;
		String sort = searchData.getSort();

		if (searchData.getCurrentPage() != null) {
			currentPage = searchData.getCurrentPage();
		} else {
			currentPage = 0;
		}

		if (searchData.getPageSize() != null) {
			pageSize = searchData.getPageSize();
		} else {
			pageSize = 3;
		}

		try {
			List<EmployeeEntity> employeeEntities = new ArrayList<EmployeeEntity>();
			Pageable paging;

			if (sort != null) {
				switch (sort) {
				case "ASC":
					paging = PageRequest.of(currentPage, pageSize, Sort.by("fullName"));
					break;
				case "DESC":
					paging = PageRequest.of(currentPage, pageSize, Sort.by("fullName").descending());
					break;
				default:
					paging = PageRequest.of(currentPage, pageSize);
					break;
				}
			} else {
				paging = PageRequest.of(currentPage, pageSize);
			}

			Page<EmployeeEntity> pageTuts;
			if (fullName == null) {
				pageTuts = employeeRepository.findAll(paging);
			} else {
				pageTuts = employeeRepository.findByFullNameContaining(fullName.trim(), paging);
			}

			employeeEntities = pageTuts.getContent();

			if (employeeEntities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			Map<String, Object> response = new HashMap<>();
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			response.put("employee", employeeEntities);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ArrayList<EmployeeEntity> findByFullName(String fullName) {
		employeeRepository.findByName(fullName);
		return employeeRepository.findByName(fullName);
	}
}

package com.wata.payslip.service.Implements;

import java.io.IOException;
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

import com.wata.payslip.model.dtos.EmployeeDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.entity.AccountEntity;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.ProjectEntity;
import com.wata.payslip.repository.AccountRepository;
import com.wata.payslip.repository.EmployeeRepository;
import com.wata.payslip.repository.FileManagerRepository;
import com.wata.payslip.service.Interface.IEmployeeService;
import com.wata.payslip.service.Interface.IFileService;

@Service
public class EmployeeService implements IEmployeeService {
	@Autowired
	public IFileService iFileService;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private FileManagerRepository fileManagerRepository;
	@Autowired
	private AccountRepository accountRepository;

	// Get List of Employee
	@Override
	public List<EmployeeDTO> getAll() {
		return ((List<EmployeeEntity>) employeeRepository.findAll()).stream().map(this::convertToEmployeeDTO)
				.collect(Collectors.toList());

	}

	// Convert Entity to DTO
	@Override
	public EmployeeDTO convertToEmployeeDTO(EmployeeEntity user) {
		EmployeeDTO userLocationDTO = new EmployeeDTO();
		userLocationDTO.setId(user.getId());
		userLocationDTO.setFullName(user.getFullName());
		userLocationDTO.setTelephone(user.getTelephone());
		userLocationDTO.setEmail(user.getAccount().getUsername());
		userLocationDTO.setBirthday(user.getBirthday());
		userLocationDTO.setAccountId(user.getAccount().getId());
		userLocationDTO.setJoinDay(user.getJoinDay());
		userLocationDTO.setStatus(user.getStatus());
		userLocationDTO.setGrossSalary(user.getGrossSalary());

		return userLocationDTO;
	}

	// Get a Employee Info
	@Override
	public Optional<EmployeeDTO> getById(int id) {
		return (employeeRepository.findById(id)).map(this::convertToEmployeeDTO);
	}

	@Override
	public List<EmployeeDTO> getByFullName(String id) {
		return ((List<EmployeeEntity>) employeeRepository.findByFullName(id)).stream().map(this::convertToEmployeeDTO)
				.collect(Collectors.toList());
	}

	// Delete a Employee
	@Override
	public ResponseEntity<Map<String, Object>> deleteEmployee(Integer id) throws RelationNotFoundException {
		try {

			EmployeeEntity employee = employeeRepository.findById(id).get();

			if (employee != null) {
				employee.getProjectEntity().remove(employee.getProjectEntity());
				for (ProjectEntity user : employee.getProjectEntity()) {
					user.getEmployee().remove(employee);
				}

				employeeRepository.save(employee);
				Integer idAccount = employee.getAccount().getId();

				employeeRepository.deleteById(id);
				accountRepository.deleteById(idAccount);

				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Create a Account of Employee
	@Override
	public ResponseEntity<Map<String, Object>> createNguoiDung(EmployeeDTO nguoiDung, String token)
			throws RelationNotFoundException {

		if (token == "false")
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		if (accountRepository.myCustomQuery2(nguoiDung.getEmail()) != null) {

			AccountEntity account = accountRepository.myCustomQuery2(nguoiDung.getEmail());

			EmployeeEntity userInfo = employeeRepository.findByAccount(account);
			if (userInfo == null) {
				userInfo = new EmployeeEntity();
			}
			account.setUsername(nguoiDung.getEmail());
			account.setToken(token);
			accountRepository.save(account);
			nguoiDung.setId(userInfo.getId());
			BeanUtils.copyProperties(nguoiDung, userInfo);
			userInfo.setAccount(account);
			employeeRepository.save(userInfo);

			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			EmployeeEntity userEntity = new EmployeeEntity();
			AccountEntity account = new AccountEntity();
			account.setUsername(nguoiDung.getEmail());
			account.setToken(token);
			accountRepository.save(account);
			BeanUtils.copyProperties(nguoiDung, userEntity);
			userEntity.setAccount(account);
			employeeRepository.save(userEntity);
			return new ResponseEntity<>(HttpStatus.OK);
		}

	}

	// Updates Account Info
	@Override
	public ResponseEntity<Map<String, Object>> updates(EmployeeDTO user, Integer Id) throws RelationNotFoundException {
		try {
			if (user.getId() != Id)
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
			EmployeeEntity employee = employeeRepository.findById(user.getId()).get();
			if (!employeeRepository.findById(user.getId()).isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			employee.setFullName(user.getFullName());
			employee.setTelephone(user.getTelephone());
			employee.setBirthday(user.getBirthday());
			employee.setJoinDay(user.getJoinDay());
			employee.setStatus(user.getStatus());
			Map<String, Object> response = new HashMap<>();
			response.put("Employee", (employeeRepository.findById(employeeRepository.save(employee).getId()))
					.map(this::convertToEmployeeDTO));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// SendEmail
	@Override
	public String sendMail(JavaMailSender emailSender, EmployeeDTO email) {
		SimpleMailMessage message = new SimpleMailMessage();

		String random = getRandomNumberInts(1, 100);
		AccountEntity user = accountRepository.myCustomQuery2(email.getEmail());
		if (user != null) {
			if (user.isActive())
				return "false";
			else {
				message.setTo(email.getEmail());
				message.setSubject("Test Simple Email");
				message.setText("Welcome to our Website. Thankyou for Register \n Click here for verify: "
						+ "\n https://www.google.com/search?token=" + random); // Send
				// Message!
				emailSender.send(message);
				return random;
			}
		} else {
			message.setTo(email.getEmail());
			message.setSubject("Test Simple Email");
			message.setText("Welcome to our Website. Thankyou for Register \n Your Token: " + "\n " + random); // Send
			// Message!
			emailSender.send(message);
			return random;
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> MailReset(JavaMailSender emailSender, EmployeeDTO email) {
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			String random = getRandomNumberInts(1, 100);
			AccountEntity user = accountRepository.myCustomQuery2(email.getEmail());
			if (user != null) {
				if (user.isActive() == false)
					return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				else {
					message.setTo(email.getEmail());
					message.setSubject("Test Simple Email");
					message.setText(
							"Please don't forget password again \n Click here for reset: " + "\n token=" + random); // Send
					// Message!
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

	// Random account verify code
	@Override
	public String getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return Long.toString(random.ints(min, (max + 1)).findFirst().getAsInt());
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchEmployeeByFullName(SearchData searchData) {
		String fullName = searchData.getSearchValue();
		Integer currentPage, pageSize;
		String sort = searchData.getSort();
		currentPage = (searchData.getCurrentPage() != null) ? searchData.getCurrentPage() : 0;
		pageSize = (searchData.getPageSize() != null) ? searchData.getPageSize() : 3;

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
			List<EmployeeDTO> page;
			if (fullName == null) {
				pageTuts = employeeRepository.findAll(paging);
				page = ((List<EmployeeEntity>) pageTuts).stream().map(this::convertToEmployeeDTO)
						.collect(Collectors.toList());

			} else {
				pageTuts = employeeRepository.findByFullNameContaining(fullName.trim(), paging);
			}

			employeeEntities = pageTuts.getContent();

			if (employeeEntities.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			Map<String, Object> response = new HashMap<>();
			List<EmployeeDTO> account = employeeEntities.stream().map(this::convertToEmployeeDTO)
					.collect(Collectors.toList());
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());
			response.put("pageSize", pageTuts.getNumberOfElements());
			response.put("employee", account);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public EmployeeDTO getByEmail(String email) {

		EmployeeEntity employee = accountRepository.findByUsername(email).get().getEmployeeEntity();
		return convertToEmployeeDTO(employee);
	}

	@Override
	public ResponseEntity<?> getImage(String email) {

		Integer id = accountRepository.findByUsername(email).get().getEmployeeEntity().getId();
		if (fileManagerRepository.findByIdEmployeeAndDescription(id, "image") == null) {
			return null;
		}
		String[] avatar = (fileManagerRepository.findByIdEmployeeAndDescription(id, "image").getIdFile().getPathFile())
				.split("/");
		try {
			return iFileService.getImage(avatar[1]);
		} catch (IOException e) {
			return new ResponseEntity<>(avatar[1], HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

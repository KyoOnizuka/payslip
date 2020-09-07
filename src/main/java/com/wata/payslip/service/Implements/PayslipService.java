package com.wata.payslip.service.Implements;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wata.payslip.converter.PayslipConverter;
import com.wata.payslip.model.dtos.PayslipDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.PayslipEntity;
import com.wata.payslip.repository.AccountRepository;
import com.wata.payslip.repository.EmployeeRepository;
import com.wata.payslip.repository.PayslipRepository;
import com.wata.payslip.service.Interface.IPayslipService;
import com.wata.payslip.utils.PagingUtil;
import com.wata.payslip.utils.SortUtil;

@Service
public class PayslipService implements IPayslipService {

	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	private PayslipRepository payslipRepository;

	@Autowired
	private PayslipConverter payslipConverter;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	private Double totalWorkingDays(PayslipEntity payslipEntity) {
		return payslipEntity.getWorkingDays() + payslipEntity.getLeaveDays();
	}

	private Double totalSalary(PayslipEntity payslipEntity) {
		return payslipEntity.getGrossSalary() + payslipEntity.getResponsibilityAllowance()
				+ payslipEntity.getPackingAllowance() + payslipEntity.getBonus() + payslipEntity.getAdvance()
				+ payslipEntity.getOtherAllowance() + payslipEntity.getOvertimePay();
	}

	private Double netAmount(PayslipEntity payslipEntity) {
		return payslipEntity.getTotalSalary() - payslipEntity.getInsurance() - payslipEntity.getPersonalIncomeTax();
	}

	@Override
	public ResponseEntity<Map<String, Object>> createPayslip(PayslipDTO payslipDTO) {
		EmployeeEntity employeeEntity = entityManager.getReference(EmployeeEntity.class, payslipDTO.getIdEmployee());
		Date date = payslipDTO.getMonth();
		date.setDate(5);
		payslipDTO.setMonth(date);

		PayslipEntity entity = payslipRepository.findOneByMonthAndEmployeeEntity(date, employeeEntity);
		Map<String, Object> response = new HashMap<>();
		if (entity != null) {
			Integer idPayslip = entity.getIdPayslip();
			entity = payslipConverter.toEntity(payslipDTO);
			try {
				entity.setIdPayslip(idPayslip);
				entity.setEmployeeEntity(employeeEntity);
				entity.setGrossSalary(employeeEntity.getGrossSalary());
				entity.setTotalWorkingDays(totalWorkingDays(entity));
				entity.setTotalSalary(totalSalary(entity));
				entity.setNetAmount(netAmount(entity));
				entity.setGrossSalary(employeeEntity.getGrossSalary());
				entity = payslipRepository.save(entity);
				response.put("Payslip", payslipConverter.toDTO(entity));
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			entity = payslipConverter.toEntity(payslipDTO);
			try {
				entity.setEmployeeEntity(employeeEntity);
				entity.setGrossSalary(employeeEntity.getGrossSalary());
				entity.setTotalWorkingDays(totalWorkingDays(entity));
				entity.setTotalSalary(totalSalary(entity));
				entity.setNetAmount(netAmount(entity));
				entity = payslipRepository.save(entity);
				response.put("Payslip", payslipConverter.toDTO(entity));
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getPayslipById(Integer idPayslip) {
		try {
			PayslipEntity entity = payslipRepository.findOneByIdPayslip(idPayslip);
			Map<String, Object> response = new HashMap<>();
			response.put("Payslip", payslipConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getAllPayslip() {
		try {
			List<PayslipDTO> dtoes = payslipConverter.toDTOs(payslipRepository.findAll());
			Map<String, Object> response = new HashMap<>();
			response.put("Payslips", dtoes);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deletePayslipById(Integer idPayslip) {
		try {
			payslipRepository.deleteById(idPayslip);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> updatePayslipById(PayslipDTO payslipDTO, Integer idPayslip) {
		PayslipEntity entity = payslipConverter.toEntity(payslipDTO);
		entity.setIdPayslip(idPayslip);
		try {
			EmployeeEntity employeeEntity = entityManager.getReference(EmployeeEntity.class,
					payslipDTO.getIdEmployee());
			entity.setEmployeeEntity(employeeEntity);
			entity.setGrossSalary(employeeEntity.getGrossSalary());
			entity.setTotalWorkingDays(totalWorkingDays(entity));
			entity.setTotalSalary(totalSalary(entity));
			entity.setNetAmount(netAmount(entity));
			entity.setGrossSalary(employeeEntity.getGrossSalary());
			entity = payslipRepository.save(entity);
			Map<String, Object> response = new HashMap<>();
			response.put("Payslip", payslipConverter.toDTO(entity));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchPayslipByIdEmployee(SearchData searchData) {
		Integer idEmployee = searchData.getIdEmployee();
		try {
			List<PayslipEntity> payslipEntities = new ArrayList<PayslipEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<PayslipEntity> pageTuts = payslipRepository.getByIdEmployee(idEmployee, paging);
			payslipEntities = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("Payslip", payslipConverter.toDTOs(payslipEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchPayslipByStatus(SearchData searchData) {
		String status = searchData.getSearchValue().trim();
		try {
			List<PayslipEntity> payslipEntities = new ArrayList<PayslipEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<PayslipEntity> pageTuts = payslipRepository.findByStatus(status.trim(), paging);
			payslipEntities = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("Payslip", payslipConverter.toDTOs(payslipEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> searchPayslipByEmail(SearchData searchData) {
		String email = searchData.getSearchValue();
		Integer idEmployee = accountRepository.findByUsername(email).get().getEmployeeEntity().getId();
		try {
			List<PayslipEntity> payslipEntities = new ArrayList<PayslipEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<PayslipEntity> pageTuts = payslipRepository.getByIdEmployee(idEmployee, paging);
			payslipEntities = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("Payslip", payslipConverter.toDTOs(payslipEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> searchPayslipByMonth(SearchData searchData) {
		String value = searchData.getSearchValue();
		String year = value.split("\\-")[1];
		String month = value.split("\\-")[0];
		try {
			List<PayslipEntity> payslipEntities = new ArrayList<PayslipEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);

			Page<PayslipEntity> pageTuts = payslipRepository.findByMonth(year, month, paging);
			payslipEntities = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("Payslip", payslipConverter.toDTOs(payslipEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> getPage(SearchData searchData) {
		try {
			List<PayslipEntity> payslipEntities = new ArrayList<PayslipEntity>();
			Pageable paging = SortUtil.sortAndPaging(searchData);
			Page<PayslipEntity> pageTuts = payslipRepository.findAll(paging);
			payslipEntities = pageTuts.getContent();
			Map<String, Object> response = new HashMap<>();
			response = PagingUtil.getConvertResponse("Payslip", payslipConverter.toDTOs(payslipEntities), pageTuts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> sendMailToEmployee(JavaMailSender emailSender, PayslipDTO payslipDTO)
			throws DocumentException, MessagingException, IOException {
		// TODO Auto-generated method stub
		String email;
		try {
			email = employeeRepository.findById(payslipDTO.getIdEmployee()).get().getAccount().getUsername();
		} catch (Exception e) {
			return new ResponseEntity<>("email not found", HttpStatus.NOT_FOUND);

		}
		ByteArrayInputStream bis = citiesReport(payslipDTO);

		// -----------email service setting
		MimeMessage mimeMessage = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(email);
		helper.setSubject("Test Simple Email");
		helper.setText(String.format("payslip"));
		helper.addAttachment("payslip.pdf", new ByteArrayResource(IOUtils.toByteArray(bis)));

		// -------------sendmail
		emailSender.send(mimeMessage);

		return ResponseEntity.ok(null);

	}

	public static ByteArrayInputStream citiesReport(PayslipDTO payslipDTO) throws com.itextpdf.text.DocumentException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//------------------convert data from DTO to Hashmap
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> response = oMapper.convertValue(payslipDTO, Map.class);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(60);

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		// ----------------Set Header of pdf table
		PdfPCell hcell;
		hcell = new PdfPCell(new Phrase("Payment Sheet", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		hcell = new PdfPCell(new Phrase("Value", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		// ----------insert payslip data into pdf table
		response.forEach((key, value) -> {
			PdfPCell cell;
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			String date = value.toString();
			if (key.equals("month")) {
				Date date1 = new Date((long) value);
				date = dateFormat.format(date1);
			}
			cell = new PdfPCell(new Phrase(key));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(date));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
		});

		// -----------add table into pdf file
		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);

		document.close();

		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public ResponseEntity<?> sendAll(Date searchData) {
		// TODO Auto-generated method stub

		try {
			List<PayslipEntity> payslipEntities = new ArrayList<PayslipEntity>();
			List<EmployeeEntity> employee = employeeRepository.findAll();

			// --------------send email for each employee
			for (EmployeeEntity entity : employee) {
				PayslipEntity payslip = payslipRepository.findOneByMonthAndEmployeeEntity(searchData, entity);
				PayslipDTO payslipDTO = payslipConverter.toDTO(payslip);
				ResponseEntity<?> response = sendMailToEmployee(emailSender, payslipDTO);
			}

			return new ResponseEntity<>(null, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("email not found", HttpStatus.NOT_FOUND);

		}

	}

}

package com.wata.payslip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.wata.payslip.model.DTO.ProjectDTO;
import com.wata.payslip.model.entity.ProjectEntity;
import com.wata.payslip.repository.ProjectRepository;
import com.wata.payslip.service.ProjectService;

@RestController
@RequestMapping("/api/employee/dd")
public class ProjectController {

	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	public ProjectRepository projectRepository;
	@Autowired
	public ProjectService projectService;

	// Get all employees info
	@GetMapping("/")
	public List<ProjectDTO> findAll() {
		return projectService.getAll();
	}

	// Get employee info base on id
	@GetMapping("/{id}")
	public ProjectEntity getById(@PathVariable("id") int Id) {
		return projectRepository.findByIdProject(Id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int Id) throws RelationNotFoundException {
		projectRepository.deleteById(Id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Update employee info base on id
	@PutMapping("/{id}")
	public Map<String, Boolean> update(@PathVariable(value = "id") int Id, @Validated @RequestBody ProjectEntity entity)
			throws RelationNotFoundException {
		Optional<ProjectEntity> projectEntity = projectRepository.findById(Id);

		final ProjectEntity updatedEmployee = projectRepository.save(projectEntity);
		Map<String, Boolean> response = new HashMap<>();
		response.put("success", Boolean.TRUE);
		return response;
	}

	@RequestMapping(value = "/project", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> createProject(@Validated @RequestBody ProjectDTO project)
			throws RelationNotFoundException {
		return projectService.createProject(project);
	}

	/*
	 * @RequestMapping(value = "/pages", method = RequestMethod.POST) public
	 * ResponseEntity<Map<String, Object>> searchEmployeeByFullName(@RequestBody
	 * SearchData searchData) { // default currentPage = 0, pageSize = 3 return
	 * employeeService.searchEmployeeByFullName(searchData); }
	 */

}

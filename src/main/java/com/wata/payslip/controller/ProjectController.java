package com.wata.payslip.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.model.dtos.ProjectDTO;
import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.service.Interface.IProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	@Autowired
	private IProjectService iProjectService;

	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createProject(@Validated @RequestBody ProjectDTO dto) {
		return iProjectService.createProject(dto);
	}

	@GetMapping("")
	public ResponseEntity<Map<String, Object>> getAllProjects() {
		return iProjectService.getAllProject();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getProjectById(@PathVariable(value = "id") Integer id) {
		return iProjectService.getProjectById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteProjectById(@PathVariable(value = "id") Integer id) {
		return iProjectService.deleteProjectById(id);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateProjectById(@Validated @RequestBody ProjectDTO dto, @PathVariable Integer id) {
		return iProjectService.updateProjectById(dto, id);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchByNameProject(@RequestBody SearchData searchData) {
		return iProjectService.searchByNameProject(searchData);
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getProjectById(@PathVariable("id") int Id) {
		return iProjectService.getProjectByEmployee(Id);
	}

}

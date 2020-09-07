package com.wata.payslip.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wata.payslip.model.dtos.AssignmentDTO;
import com.wata.payslip.service.Interface.IAssignmentService;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

	@Autowired
	public IAssignmentService iAssignmentService;

	@GetMapping("")
	public List<AssignmentDTO> GetAllByEmployee() {

		return iAssignmentService.getAllEmployee();
	}

	@GetMapping("/project")
	public List<AssignmentDTO> GetByProject() {

		return iAssignmentService.getByProject();
	}

	@GetMapping("/{id}")
	public Optional<AssignmentDTO> GetByIdEmployee(@PathVariable(value = "id") Integer id) {
		return iAssignmentService.getByIdEmployee(id);
	}

	@GetMapping("/project/{id}")
	public Optional<AssignmentDTO> GetByIdProject(@PathVariable(value = "id") Integer id) {
		return iAssignmentService.getByIdProject(id);
	}

	@RequestMapping(value = "/", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> Assignment(@RequestBody AssignmentDTO assignment) {
		return iAssignmentService.Assignment(assignment);
	}

	@DeleteMapping(value = "/")
	public ResponseEntity<?> Delete(@RequestBody AssignmentDTO assignment) {
		return iAssignmentService.Delete(assignment);

	}

}

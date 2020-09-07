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

import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.WorkDTO;
import com.wata.payslip.service.Interface.IWorkService;

@RestController
@RequestMapping("/api/work")
public class WorkController {
	@Autowired
	private IWorkService iWorkService;

	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createWork(@Validated @RequestBody WorkDTO workDTO) {
		return iWorkService.createWork(workDTO);
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllWork() {
		return iWorkService.getAllWork();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getWorktById(@PathVariable(value = "id") Integer idWork) {
		return iWorkService.getWorkById(idWork);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteWorkById(@PathVariable(value = "id") Integer idWork) {
		return iWorkService.deleteWorkById(idWork);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateWorkById(@RequestBody WorkDTO workDTO, @PathVariable Integer id) {
		return iWorkService.updateWorkById(workDTO, id);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchWorkBySummary(@RequestBody SearchData searchData) {
		return iWorkService.searchWorkBySummary(searchData);
	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchWorkByStatus(@RequestBody SearchData searchData) {
		return iWorkService.searchWorkByStatus(searchData);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchWork(@RequestBody SearchData searchData) {
		return iWorkService.searchWorkByEmployee(searchData);
	}

}

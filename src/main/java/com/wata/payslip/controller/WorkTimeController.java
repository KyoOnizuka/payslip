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
import com.wata.payslip.model.dtos.WorkTimeDTO;
import com.wata.payslip.service.Interface.IWorkTimeService;

@RestController
@RequestMapping("/api/worktime")
public class WorkTimeController {
	@Autowired
	private IWorkTimeService iWorkTimeService;

	@RequestMapping(value = "/create", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createWorkTime(@Validated @RequestBody WorkTimeDTO workTimeDTO) {
		return iWorkTimeService.createWorkTime(workTimeDTO);
	}

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllWorkTime() {
		return iWorkTimeService.getAllWorkTime();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getWorkTimetById(@PathVariable(value = "id") Integer idWorktime) {
		return iWorkTimeService.getWorkTimeById(idWorktime);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteWorkTimeById(@PathVariable(value = "id") Integer idWorktime) {
		return iWorkTimeService.deleteWorkTimeById(idWorktime);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateWorkTimekById(@RequestBody WorkTimeDTO workTimeDTO,
			@PathVariable Integer id) {
		return iWorkTimeService.updateWorkTimekById(workTimeDTO, id);
	}

	@RequestMapping(value = "/pages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchWorkTimeBySummaryAndEmployee(@RequestBody SearchData searchData) {
		return iWorkTimeService.searchWorkTimeBySummaryAndEmployee(searchData);
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchWorkTimeByEmployee(@RequestBody SearchData searchData) {
		return iWorkTimeService.searchWorkTimeByEmployee(searchData);
	}
}

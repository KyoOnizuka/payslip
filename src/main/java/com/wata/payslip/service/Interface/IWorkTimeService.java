package com.wata.payslip.service.Interface;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.WorkTimeDTO;

public interface IWorkTimeService {
	public ResponseEntity<Map<String, Object>> createWorkTime(WorkTimeDTO workTimeDTO);

	public ResponseEntity<Map<String, Object>> getAllWorkTime();

	public ResponseEntity<Map<String, Object>> getWorkTimeById(Integer idWorktime);

	public ResponseEntity<Map<String, Object>> deleteWorkTimeById(Integer idWorktime);

	public ResponseEntity<Map<String, Object>> updateWorkTimekById(WorkTimeDTO workTimeDTO, Integer idWorktime);

	public ResponseEntity<Map<String, Object>> searchWorkTimeBySummaryAndEmployee(SearchData searchData);

	public ResponseEntity<Map<String, Object>> searchWorkTimeByEmployee(SearchData searchData);

}

package com.wata.payslip.service.Interface;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.WorkDTO;

public interface IWorkService {
	public ResponseEntity<Map<String, Object>> createWork(WorkDTO workDTO);

	public ResponseEntity<Map<String, Object>> getAllWork();

	public ResponseEntity<Map<String, Object>> getWorkById(Integer idWork);

	public ResponseEntity<Map<String, Object>> deleteWorkById(Integer idWork);

	public ResponseEntity<Map<String, Object>> updateWorkById(WorkDTO workDTO, Integer idWork);

	public ResponseEntity<Map<String, Object>> searchWorkBySummary(SearchData searchData);

	public ResponseEntity<Map<String, Object>> searchWorkByStatus(SearchData searchData);

	public ResponseEntity<Map<String, Object>> searchWorkByEmployee(SearchData searchData);

}

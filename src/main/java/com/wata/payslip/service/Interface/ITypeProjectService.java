package com.wata.payslip.service.Interface;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.wata.payslip.model.dtos.SearchData;
import com.wata.payslip.model.dtos.TypeProjectDTO;

public interface ITypeProjectService {
	ResponseEntity<Map<String, Object>> createTypePreject(TypeProjectDTO typeProjectDTO);

	ResponseEntity<Map<String, Object>> getAllTypeProject();

	ResponseEntity<Map<String, Object>> getTypeProjectById(Integer id);

	ResponseEntity<Map<String, Object>> deleteTypeProjectById(Integer id);

	ResponseEntity<Map<String, Object>> updateTypeProjectById(TypeProjectDTO typeProjectDTO, Integer id);

	ResponseEntity<Map<String, Object>> searchTypeByTypeName(SearchData searchData);
}

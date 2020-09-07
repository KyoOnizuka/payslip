package com.wata.payslip.service.Interface;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.wata.payslip.model.dtos.ProjectDTO;
import com.wata.payslip.model.dtos.SearchData;

public interface IProjectService {
	public ResponseEntity<Map<String, Object>> createProject(ProjectDTO projectDTO);

	public ResponseEntity<Map<String, Object>> getAllProject();

	public ResponseEntity<Map<String, Object>> getProjectById(Integer idProject);

	public ResponseEntity<Map<String, Object>> deleteProjectById(Integer idProject);

	public ResponseEntity<?> updateProjectById(ProjectDTO projectDTO, Integer idProject);

	public ResponseEntity<Map<String, Object>> searchByNameProject(SearchData searchData);

	public ResponseEntity<?> getProjectByEmployee(int id);
}

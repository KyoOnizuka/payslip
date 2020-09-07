package com.wata.payslip.service.Interface;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.wata.payslip.model.dtos.AssignmentDTO;
import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.ProjectEntity;

public interface IAssignmentService {

	AssignmentDTO convertToEmployeeDTO(EmployeeEntity user);

	List<AssignmentDTO> getAllEmployee();

	AssignmentDTO convertToProjectDTO(ProjectEntity user);

	List<AssignmentDTO> getByProject();

	ResponseEntity<?> Assignment(AssignmentDTO assignment);

	Optional<AssignmentDTO> getByIdEmployee(Integer id);

	Optional<AssignmentDTO> getByIdProject(Integer id);

	ResponseEntity<?> Delete(AssignmentDTO assignment);

}

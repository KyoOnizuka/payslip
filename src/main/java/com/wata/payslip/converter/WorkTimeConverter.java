package com.wata.payslip.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wata.payslip.model.dtos.WorkTimeDTO;
import com.wata.payslip.model.entity.WorkTimeEntity;

@Component
public class WorkTimeConverter {
	public WorkTimeEntity toEntity(WorkTimeDTO dto) {
		WorkTimeEntity entity = new WorkTimeEntity();
		entity.setStartDate(dto.getStartDate());
		entity.setHour(dto.getHour());
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		return entity;
	}

	public WorkTimeDTO toDTO(WorkTimeEntity entity) {
		WorkTimeDTO dto = new WorkTimeDTO();
		dto.setIdWorktime(entity.getIdWorktime());
		dto.setIdWork(entity.getWorkEntity().getIdWork());
		dto.setIdEmployee(entity.getEmployeeEntity().getId());
		dto.setStartDate(entity.getStartDate());
		dto.setHour(entity.getHour());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setFullName(entity.getEmployeeEntity().getFullName());
		dto.setSummary(entity.getWorkEntity().getSummary());
		return dto;
	}

	public List<WorkTimeDTO> toDTOs(List<WorkTimeEntity> entities) {
		List<WorkTimeDTO> dtoes = new ArrayList<WorkTimeDTO>();
		for (WorkTimeEntity workTimeEntity : entities) {
			WorkTimeDTO dto = this.toDTO(workTimeEntity);
			dtoes.add(dto);
		}
		return dtoes;
	}
}

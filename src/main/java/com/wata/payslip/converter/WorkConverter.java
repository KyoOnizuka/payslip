package com.wata.payslip.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wata.payslip.model.dtos.WorkDTO;
import com.wata.payslip.model.entity.WorkEntity;

@Component
public class WorkConverter {
	public WorkEntity toEntity(WorkDTO dto) {
		WorkEntity entity = new WorkEntity();
		entity.setSummary(dto.getSummary());
		entity.setDescription(dto.getDescription());
		entity.setStatus(dto.getStatus());
		return entity;
	}

	public WorkDTO toDTO(WorkEntity entity) {
		WorkDTO dto = new WorkDTO();
		dto.setIdWork(entity.getIdWork());
		dto.setIdProject(entity.getProjectEntity().getIdProject());
		dto.setDescription(entity.getDescription());
		dto.setSummary(entity.getSummary());
		dto.setStatus(entity.getStatus());
		dto.setNameProject(entity.getProjectEntity().getNameProject());
		return dto;
	}

	public List<WorkDTO> toDTOs(List<WorkEntity> entities) {
		List<WorkDTO> dtoes = new ArrayList<WorkDTO>();
		for (WorkEntity workEntity : entities) {
			WorkDTO dto = this.toDTO(workEntity);
			dtoes.add(dto);
		}
		return dtoes;
	}
}

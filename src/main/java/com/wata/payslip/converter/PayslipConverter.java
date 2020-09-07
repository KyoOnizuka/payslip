package com.wata.payslip.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wata.payslip.model.dtos.PayslipDTO;
import com.wata.payslip.model.entity.PayslipEntity;

@Component
public class PayslipConverter {
	public PayslipEntity toEntity(PayslipDTO dto) {
		PayslipEntity entity = new PayslipEntity();
		entity.setAdvance(dto.getAdvance());
		entity.setBonus(dto.getBonus());
		entity.setLeaveDays(dto.getLeaveDays());
		entity.setMonth(dto.getMonth());
		entity.setNetAmount(dto.getNetAmount());
		entity.setOtherAllowance(dto.getOtherAllowance());
		entity.setOvertimePay(dto.getOvertimePay());
		entity.setPackingAllowance(dto.getPackingAllowance());
		entity.setPersonalIncomeTax(dto.getPersonalIncomeTax());
		entity.setResponsibilityAllowance(dto.getResponsibilityAllowance());
		entity.setTotalSalary(dto.getTotalSalary());
		entity.setTotalWorkingDays(dto.getTotalWorkingDays());
		entity.setWorkingDays(dto.getWorkingDays());
		entity.setStatus(dto.getStatus());
		return entity;
	}

	public PayslipDTO toDTO(PayslipEntity entity) {
		PayslipDTO dto = new PayslipDTO();
		dto.setAdvance(entity.getAdvance());
		dto.setBonus(entity.getBonus());
		dto.setGrossSalary(entity.getGrossSalary());
		dto.setIdEmployee(entity.getEmployeeEntity().getId());
		dto.setIdPayslip(entity.getIdPayslip());
		dto.setInsurance(entity.getInsurance());
		dto.setLeaveDays(entity.getLeaveDays());
		dto.setMonth(entity.getMonth());
		dto.setNetAmount(entity.getNetAmount());
		dto.setOtherAllowance(entity.getOtherAllowance());
		dto.setOvertimePay(entity.getOvertimePay());
		dto.setPackingAllowance(entity.getPackingAllowance());
		dto.setPersonalIncomeTax(entity.getPersonalIncomeTax());
		dto.setResponsibilityAllowance(entity.getResponsibilityAllowance());
		dto.setTotalSalary(entity.getTotalSalary());
		dto.setTotalWorkingDays(entity.getTotalWorkingDays());
		dto.setWorkingDays(entity.getWorkingDays());
		dto.setFullName(entity.getEmployeeEntity().getFullName());
		dto.setStatus(entity.getStatus());
		return dto;
	}

	public List<PayslipDTO> toDTOs(List<PayslipEntity> entities) {
		List<PayslipDTO> dtoes = new ArrayList<PayslipDTO>();
		for (PayslipEntity payslipEntity : entities) {
			PayslipDTO dto = this.toDTO(payslipEntity);
			dtoes.add(dto);
		}
		return dtoes;
	}
}

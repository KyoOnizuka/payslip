package com.wata.payslip.model.dtos;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PayslipDTO {

	private Integer idPayslip;
	private Integer idEmployee;
	private Double workingDays;
	private Double leaveDays;
	private Double totalWorkingDays;
	private Double grossSalary;
	private Double responsibilityAllowance;
	private Double packingAllowance;
	private Double bonus;
	private Double advance;
	private Double otherAllowance;
	private Double overtimePay;
	@DateTimeFormat(pattern = "yyyy-mm")
	private Date month;
	private Double totalSalary;
	private Double insurance;
	private Double personalIncomeTax;
	private Double netAmount;
	private String fullName;
	private String status;

	public PayslipDTO() {

	}

	public PayslipDTO(Integer idPayslip, Integer idEmployee, Double workingDays, Double leaveDays, Double grossSalary,
			Double responsibilityAllowance, Double packingAllowance, Double bonus, Double advance,
			Double otherAllowance, Double overtimePay, Date month, Double insurance, Double personalIncomeTax,
			String fullName, String status) {
		super();
		this.idPayslip = idPayslip;
		this.idEmployee = idEmployee;
		this.workingDays = workingDays;
		this.leaveDays = leaveDays;
		this.grossSalary = grossSalary;
		this.responsibilityAllowance = responsibilityAllowance;
		this.packingAllowance = packingAllowance;
		this.bonus = bonus;
		this.advance = advance;
		this.otherAllowance = otherAllowance;
		this.overtimePay = overtimePay;
		this.month = month;
		this.insurance = insurance;
		this.personalIncomeTax = personalIncomeTax;
		this.fullName = fullName;
		this.status = status;
	}

	public Integer getIdPayslip() {
		return idPayslip;
	}

	public void setIdPayslip(Integer idPayslip) {
		this.idPayslip = idPayslip;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public Double getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(Double workingDays) {
		this.workingDays = workingDays;
	}

	public Double getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(Double leaveDays) {
		this.leaveDays = leaveDays;
	}

	public Double getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(Double totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public Double getResponsibilityAllowance() {
		return responsibilityAllowance;
	}

	public void setResponsibilityAllowance(Double responsibilityAllowance) {
		this.responsibilityAllowance = responsibilityAllowance;
	}

	public Double getPackingAllowance() {
		return packingAllowance;
	}

	public void setPackingAllowance(Double packingAllowance) {
		this.packingAllowance = packingAllowance;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Double getAdvance() {
		return advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public Double getOtherAllowance() {
		return otherAllowance;
	}

	public void setOtherAllowance(Double otherAllowance) {
		this.otherAllowance = otherAllowance;
	}

	public Double getOvertimePay() {
		return overtimePay;
	}

	public void setOvertimePay(Double overtimePay) {
		this.overtimePay = overtimePay;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	public Double getPersonalIncomeTax() {
		return personalIncomeTax;
	}

	public void setPersonalIncomeTax(Double personalIncomeTax) {
		this.personalIncomeTax = personalIncomeTax;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

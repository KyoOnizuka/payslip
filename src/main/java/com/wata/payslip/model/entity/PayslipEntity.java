package com.wata.payslip.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Payslip")
public class PayslipEntity {
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "IdEmployee", nullable = false)
	private EmployeeEntity employeeEntity;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdPayslip")
	private Integer idPayslip;

	@Column(name = "WorkingDays")
	private Double workingDays;

	@Column(name = "LeaveDays")
	private Double leaveDays;

	@Column(name = "TotalWorkingDays")
	private Double totalWorkingDays;

	@Column(name = "GrossSalary")
	private Double grossSalary;

	@Column(name = "ResponsibilityAllowance")
	private Double responsibilityAllowance;

	@Column(name = "PackingAllowance")
	private Double packingAllowance;

	@Column(name = "Bonus")
	private Double bonus;

	@Column(name = "Advance")
	private Double advance;

	@Column(name = "OtherAllowance")
	private Double otherAllowance;

	@Column(name = "OvertimePay")
	private Double overtimePay;

	@Column(name = "Month")
	@DateTimeFormat(pattern = "yyyy-mm")
	private Date month;

	@Column(name = "TotalSalary")
	private Double totalSalary;

	@Column(name = "Insurance")
	private Double insurance = (10.5 / 100) * 4729000;

	@Column(name = "PersonalIncomeTax")
	private Double personalIncomeTax;

	@Column(name = "NetAmount")
	private Double netAmount;

	@Column(name = "Status")
	private String status;

	public PayslipEntity() {

	}

	public PayslipEntity(EmployeeEntity employeeEntity, Integer idPayslip, Double workingDays, Double leaveDays,
			Double totalWorkingDays, Double grossSalary, Double responsibilityAllowance, Double packingAllowance,
			Double bonus, Double advance, Double otherAllowance, Double overtimePay, Date month, Double totalSalary,
			Double insurance, Double personalIncomeTax, Double netAmount, String status) {
		this.employeeEntity = employeeEntity;
		this.idPayslip = idPayslip;
		this.workingDays = workingDays;
		this.leaveDays = leaveDays;
		this.totalWorkingDays = totalWorkingDays;
		this.grossSalary = grossSalary;
		this.responsibilityAllowance = responsibilityAllowance;
		this.packingAllowance = packingAllowance;
		this.bonus = bonus;
		this.advance = advance;
		this.otherAllowance = otherAllowance;
		this.overtimePay = overtimePay;
		this.month = month;
		this.totalSalary = totalSalary;
		this.insurance = insurance;
		this.personalIncomeTax = personalIncomeTax;
		this.netAmount = netAmount;
		this.status = status;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public Integer getIdPayslip() {
		return idPayslip;
	}

	public void setIdPayslip(Integer idPayslip) {
		this.idPayslip = idPayslip;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

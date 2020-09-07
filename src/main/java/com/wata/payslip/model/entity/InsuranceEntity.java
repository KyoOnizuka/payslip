package com.wata.payslip.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "insurance")
public class InsuranceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdInsurance")
	private Integer idInsurance;

	@Column(name = "Name", columnDefinition = "nvarchar(255)")
	private String name;

	@Column(name = "Value")
	private Double value;

	public InsuranceEntity() {

	}

	public InsuranceEntity(Integer idInsurance, String name, Double value) {
		this.idInsurance = idInsurance;
		this.name = name;
		this.value = value;
	}

	public Integer getIdInsurance() {
		return idInsurance;
	}

	public void setIdInsurance(Integer idInsurance) {
		this.idInsurance = idInsurance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}

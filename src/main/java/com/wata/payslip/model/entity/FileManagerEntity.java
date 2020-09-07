package com.wata.payslip.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "fileManager")
public class FileManagerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idEmployee", nullable = false)
	private EmployeeEntity idEmployee;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idFile", nullable = false)
	private FileEntity idFile;
	@Column(name = "description", nullable = false)
	private String description;

	public FileManagerEntity() {

	}

	public FileManagerEntity(Integer id, FileEntity idFile, EmployeeEntity idEmployee, String description) {
		this.id = id;
		this.idEmployee = idEmployee;
		this.idFile = idFile;
		this.description = description;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmployeeEntity getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(EmployeeEntity idEmployee) {
		this.idEmployee = idEmployee;
	}

	public FileEntity getIdFile() {
		return idFile;
	}

	public void setIdFile(FileEntity idFile) {
		this.idFile = idFile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
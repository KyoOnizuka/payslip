package com.wata.payslip.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filestorage")
public class FileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idFile", nullable = false)
	private Integer idFile;
	@Column(name = "nameFile", nullable = false, columnDefinition = "nvarchar(255)")
	private String nameFile;
	@Column(name = "pathFile", nullable = false, columnDefinition = "nvarchar(255)")
	private String pathFile;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "idFile")
	private FileManagerEntity fileManager;

	public FileEntity() {

	}

	public FileEntity(Integer idFile, String nameFile, String pathFile) {
		this.idFile = idFile;
		this.nameFile = nameFile;
		this.pathFile = pathFile;
	}

	public Integer getIdFile() {
		return idFile;
	}

	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public FileManagerEntity getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManagerEntity fileManager) {
		this.fileManager = fileManager;
	}

}

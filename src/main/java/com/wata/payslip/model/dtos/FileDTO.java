package com.wata.payslip.model.dtos;

public class FileDTO {

	private Integer idFile;
	private String nameFile;
	private String pathFile;

	public FileDTO() {

	}

	public FileDTO(Integer idFile, String nameFile, String pathFile) {
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

}

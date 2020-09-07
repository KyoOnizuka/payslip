package com.wata.payslip.model.dtos;

import javax.validation.constraints.Pattern;

public class TypeProjectDTO {
	private int id;
	@Pattern(regexp = "[A-Za-z0-9 \\t\\n\\x0B\\f\\r\\p{L}\\.(&*-_().,:)]+")
	private String typeName;

	public TypeProjectDTO() {
	}

	public TypeProjectDTO(int id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}

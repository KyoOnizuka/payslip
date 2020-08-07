package com.wata.payslip.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "typeProject")
public class TypeProjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdTypeProject")
	private int id;

	@Column(name = "TypeName")
	private String typeName;

	@OneToMany(mappedBy = "typeProject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ProjectEntity> projectEntity;

	public TypeProjectEntity() {

	}

	public TypeProjectEntity(int id, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;

	}

	@Column(name = "IdTypeProject")
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
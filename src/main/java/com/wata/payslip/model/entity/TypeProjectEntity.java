package com.wata.payslip.model.entity;

import java.util.List;

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

	@OneToMany(mappedBy = "typeProject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProjectEntity> projectEntities;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdTypeProject")
	private int id;

	@Column(name = "TypeName", columnDefinition = "nvarchar(255)")
	private String typeName;

	public TypeProjectEntity() {

	}

	public TypeProjectEntity(int id, String typeName) {
		this.id = id;
		this.typeName = typeName;

	}

	public List<ProjectEntity> getProjectEntities() {
		return projectEntities;
	}

	public void setProjectEntities(List<ProjectEntity> projectEntities) {
		this.projectEntities = projectEntities;
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

	public void addProject(ProjectEntity projectEntity) {
		this.projectEntities.add(projectEntity);
	}

}
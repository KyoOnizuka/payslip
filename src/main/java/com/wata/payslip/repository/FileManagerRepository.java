package com.wata.payslip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wata.payslip.model.entity.FileManagerEntity;

public interface FileManagerRepository extends JpaRepository<FileManagerEntity, Integer> {

	public FileManagerEntity findByIdEmployee(Integer i);

	public FileManagerEntity findByIdFile(Integer i);

	@Query(value = "select * from file_manager u where u.id_employee = ?1 and u.description = ?2", nativeQuery = true)
	public FileManagerEntity findByIdEmployeeAndDescription(Integer idEmployee, String description);

}

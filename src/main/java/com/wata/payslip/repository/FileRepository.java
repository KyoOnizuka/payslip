package com.wata.payslip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wata.payslip.model.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
	Optional<FileEntity> findByPathFile(String name);

}

package com.wata.payslip.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wata.payslip.model.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

	Page<ProjectEntity> findByNameProjectContaining(String nameProject, Pageable pageable);

	Optional<ProjectEntity> findOneByIdProject(Integer idProject);

	Optional<ProjectEntity> findByIdProject(Integer idProject);
}
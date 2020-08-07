package com.wata.payslip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wata.payslip.model.entity.TypeProjectEntity;

@Repository
public interface TypeProjectRepository extends JpaRepository<TypeProjectEntity, Integer> {

}

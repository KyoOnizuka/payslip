package com.wata.payslip.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wata.payslip.model.entity.WorkEntity;

@Repository
public interface WorkRepository extends JpaRepository<WorkEntity, Integer> {
	Page<WorkEntity> findBySummaryContaining(String summary, Pageable pageable);

	WorkEntity findOneByIdWork(Integer idWork);

	List<WorkEntity> findBySummaryContaining(String summary);

	@Query("SELECT w FROM WorkEntity w WHERE w.status not in ('DONE')")
	Page<WorkEntity> findStatus(Pageable pageable);

	@Query(value = "SELECT * FROM Work w WHERE w.status != 'DONE' and w.id_project in (SELECT a.project_id  FROM assignment a WHERE a.project_id = :idProject and a.employee_id = :idEmployee)", nativeQuery = true)
	Page<WorkEntity> getByEmployee(@Param("idProject") Integer idProject, @Param("idEmployee") Integer idEmployee,
			Pageable pageable);

	@Query(value = "SELECT * FROM Work w WHERE w.status != 'DONE' and w.id_project in (SELECT a.project_id  FROM assignment a WHERE a.employee_id = :idEmployee)", nativeQuery = true)
	Page<WorkEntity> getWorkByIdEmployee(@Param("idEmployee") Integer idEmployee, Pageable pageable);

}
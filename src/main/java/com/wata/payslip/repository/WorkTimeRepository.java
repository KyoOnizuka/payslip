package com.wata.payslip.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wata.payslip.model.entity.WorkTimeEntity;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTimeEntity, Integer> {

	WorkTimeEntity findOneByIdWorktime(Integer idWorktime);

	@Query("select w FROM WorkTimeEntity w WHERE w.title like %:value% or w.employeeEntity in (select e.id from EmployeeEntity e where e.fullName like %:value2%)	 or w.workEntity in (select we.idWork from WorkEntity we where we.summary like %:value3%)")
	Page<WorkTimeEntity> findFull(@Param("value") String value, @Param("value2") String value2,
			@Param("value3") String value3, Pageable pageable);

	@Query(value = "SELECT * FROM work_time w WHERE w.id_employee = :idEmployee ORDER BY start_date DESC", nativeQuery = true)
	List<WorkTimeEntity> getWorkTimeByWork(@Param("idEmployee") Integer idEmployee);

	@Query(value = "select * from work_time as wt where wt.id_work in (select w.id_work from work as w where wt.id_employee =:idEmployee and w.id_project in (select a.project_id from assignment as a where a.project_id =:idProject)) ORDER BY start_date DESC", nativeQuery = true)
	List<WorkTimeEntity> getWorkTimeByEmployeeAndProject(@Param("idEmployee") Integer idEmployee,
			@Param("idProject") Integer idProject);

}
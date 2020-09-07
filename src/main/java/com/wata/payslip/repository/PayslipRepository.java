package com.wata.payslip.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wata.payslip.model.entity.EmployeeEntity;
import com.wata.payslip.model.entity.PayslipEntity;

@Repository
public interface PayslipRepository extends JpaRepository<PayslipEntity, Integer> {

	PayslipEntity findOneByIdPayslip(Integer idPayslip);

	@Query(value = "Select * from payslip p where p.id_employee = :idEmployee ORDER BY p.month DESC", nativeQuery = true)
	Page<PayslipEntity> getByIdEmployee(@Param("idEmployee") Integer idEmployee, Pageable pageable);

	@Query(value = "select * from payslip p where p.status like %:status%", nativeQuery = true)
	Page<PayslipEntity> findByStatus(@Param("status") String status, Pageable pageable);

	@Query(value = "select * from payslip p where year(p.month) = :year and month(p.month) = :month", nativeQuery = true)
	Page<PayslipEntity> findByMonth(@Param("year") String year, @Param("month") String month, Pageable pageable);

	PayslipEntity findOneByMonthAndEmployeeEntity(Date date, EmployeeEntity employeeEntity);
}
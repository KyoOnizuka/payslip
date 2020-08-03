package com.wata.payslip.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.wata.payslip.model.EmployeeEntity;


public class EmployeeSpecification implements Specification<EmployeeEntity> {
	private SearchCriteria criteria;
	
	public EmployeeSpecification(SearchCriteria searchCriteria) {
		// TODO Auto-generated constructor stub
		this.criteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<EmployeeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		 if (criteria.getOperation().equalsIgnoreCase(">")) {
	            return criteriaBuilder.greaterThanOrEqualTo(
	              root.<String> get(criteria.getKey()), criteria.getValue().toString());
	        } 
		 else if (criteria.getOperation().equalsIgnoreCase("<")) {
	            return criteriaBuilder.lessThanOrEqualTo(
	              root.<String> get(criteria.getKey()), criteria.getValue().toString());
	      } 
		 else if (criteria.getOperation().equalsIgnoreCase(":")) {
	            if (root.get(criteria.getKey()).getJavaType() == String.class) {
	                return criteriaBuilder.like(
	                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
	            } else {
	                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
	            }
	        }
		return null;
	}

	
}
package com.wata.payslip.employeeDTO;

public class searchString {

	public String searString;
	public Integer currentPage, pageSize;
	public String sort;
	
	public searchString() {
		
	}
	
	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getSearString() {
		return searString;
	}
	
	public void setSearString(String searString) {
		this.searString = searString;
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
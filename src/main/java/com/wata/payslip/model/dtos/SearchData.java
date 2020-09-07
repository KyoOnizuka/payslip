package com.wata.payslip.model.dtos;

public class SearchData {
	private String searchValue;
	private Integer currentPage, pageSize;
	private String sort;
	private String sortKey;
	private Integer idProject;
	private Integer idEmployee;
	private String month;
	private String year;

	public SearchData() {

	}

	public SearchData(String searchValue, Integer currentPage, Integer pageSize, String sort, String sortKey,
			Integer idProject, Integer idEmployee, String month, String year) {
		this.searchValue = searchValue;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.sort = sort;
		this.sortKey = sortKey;
		this.idProject = idProject;
		this.idEmployee = idEmployee;
		this.month = month;
		this.year = year;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public Integer getIdProject() {
		return idProject;
	}

	public void setIdProject(Integer idProject) {
		this.idProject = idProject;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
package com.wata.payslip.employeeDTO;
import java.util.List;

public class PaginationList<T> {
	public List<T> pageOfItem;
	public int currentPage, totalPage, pageSize, totalItem;

	public PaginationList(List<T> pageOfItem, int currentPage, int totalPage, int totalItem) {
		this.pageOfItem = pageOfItem;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalItem = totalItem;
	}

	public List<T> getPageOfItem() {
		return pageOfItem;
	}

	public void setPageOfItem(List<T> pageOfItem) {
		this.pageOfItem = pageOfItem;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
}
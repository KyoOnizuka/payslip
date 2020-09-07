package com.wata.payslip.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.wata.payslip.model.dtos.SearchData;

public class SortUtil {

	public static Pageable sortAndPaging(SearchData searchData) {
		Integer currentPage, pageSize;
		String sort = searchData.getSort();
		String sortKey = searchData.getSortKey();

		currentPage = (searchData.getCurrentPage() != null) ? searchData.getCurrentPage()
				: ApplicationSettings.CURRENT_PAGE;
		pageSize = (searchData.getPageSize() != null) ? searchData.getPageSize() : ApplicationSettings.PAGE_SIZE;

		Pageable paging;
		if (sortKey != null && sort != null) {
			switch (sort) {
			case "DESC":
				paging = PageRequest.of(currentPage, pageSize, Sort.by(sortKey).descending());
				break;
			default:
				paging = PageRequest.of(currentPage, pageSize, Sort.by(sortKey));
				break;
			}
		} else {
			paging = PageRequest.of(currentPage, pageSize);
		}
		return paging;
	}
}

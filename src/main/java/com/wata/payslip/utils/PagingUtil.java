package com.wata.payslip.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public class PagingUtil {
	public static Map<String, Object> getConvertResponse(String key, List<?> dto, Page page) {
		Map<String, Object> response = new HashMap<>();
		if (dto.isEmpty()) {
			response.put(key, dto);
			response.put("currentPage", 0);
			response.put("totalItems", 0);
			response.put("totalPages", 0);
			response.put("pageSize", 0);
			return response;
		}
		response.put("currentPage", page.getNumber());
		response.put("totalItems", page.getTotalElements());
		response.put("totalPages", page.getTotalPages());
		response.put("pageSize", page.getNumberOfElements());
		response.put(key, dto);
		return response;
	}
}
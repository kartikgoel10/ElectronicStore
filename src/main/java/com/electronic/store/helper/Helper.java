package com.electronic.store.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.electronic.store.dtos.PageableResponse;

public class Helper {

	public static <U, V> PageableResponse<U> getPageableResponse(Page<V> page, Class<U> type) {
		List<V> entity = page.getContent();
		List<U> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type))
				.collect(Collectors.toList());
		PageableResponse<U> pageableResponse = new PageableResponse<>();
		pageableResponse.setContent(dtoList);
		pageableResponse.setPageNumber(page.getNumber());
		pageableResponse.setPageSize(page.getSize());
		pageableResponse.setTotalElements(page.getTotalElements());
		pageableResponse.setTotalPages(page.getTotalPages());
		pageableResponse.setLastPage(page.isLast());
		return pageableResponse;
	}

}

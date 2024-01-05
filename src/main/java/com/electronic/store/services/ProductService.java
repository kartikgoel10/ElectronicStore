package com.electronic.store.services;

import java.util.List;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;

public interface ProductService {
	
	//create
	ProductDto create(ProductDto productDto);
	
	
	//update
	ProductDto update(ProductDto productDto, String productId);
	
	//delete
	void delete(String productId);
	
	//get all
	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get single
	ProductDto get(String productId);
	
	//get all : live
	PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//search product
	PageableResponse<ProductDto> searchByTitle(String subTitle , int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//other methods

}

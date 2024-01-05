package com.electronic.store.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.helper.ApiResponseMessage;
import com.electronic.store.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	/*
	 * create products
	 * 
	 * @Param productDto
	 */
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
		ProductDto createdProduct = productService.create(productDto);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	/*
	 * update prodcuts
	 * 
	 * @Param productDto , @Param productId
	 */
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable String productId,
			@RequestBody ProductDto productDto) {
		ProductDto updatedProduct = productService.update(productDto, productId);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	/*
	 * delete product
	 * 
	 * @Param productId
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
		productService.delete(productId);
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product is deleted Successfully")
				.status(HttpStatus.OK).success(false).build();
		return new ResponseEntity<ApiResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/*
	 * get single product
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
		ProductDto productDto = productService.get(productId);
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
	}

	/*
	 * get all products
	 */
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse, HttpStatus.OK);
	}

	/*
	 * get all live url : products/live
	 */
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy,
				sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse, HttpStatus.OK);
	}

	/*
	 * search all products
	 */

	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProducts(@PathVariable String query,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize,
				sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse, HttpStatus.OK);
	}

}

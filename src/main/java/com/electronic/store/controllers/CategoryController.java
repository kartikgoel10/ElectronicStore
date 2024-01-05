package com.electronic.store.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.helper.ApiResponseMessage;
import com.electronic.store.helper.ImageResponse;
import com.electronic.store.services.CategoryService;
import com.electronic.store.services.FileService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileService fileService;

	@Value("${category.coverimage.path}")
	private String imageUploadPath;

	// create
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = categoryService.create(categoryDto);
		return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") String categoeyId) {
		CategoryDto updatedCategory = categoryService.update(categoryDto, categoeyId);
		return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
	}

	// delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId) {
		categoryService.delete(categoryId);
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Category has been deleted")
				.success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage, HttpStatus.OK);
	}

	// get all Categories
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<CategoryDto> allCategories = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(allCategories, HttpStatus.OK);
	}

	// get single
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") String categoryId) {
		CategoryDto category = categoryService.get(categoryId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	// upload category image
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile categoryImage,
			@PathVariable("categoryId") String categoryId) throws IOException {
		String imageName = fileService.uploadFile(categoryImage, imageUploadPath);
		CategoryDto categoryDto = categoryService.get(categoryId);
		categoryDto.setCoverImage(imageName);
		CategoryDto updatedDto = categoryService.update(categoryDto, categoryId);
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED)
				.success(true).build();
		return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
	}

	@GetMapping("/image/{categoryId}")
	public void serviceImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
		CategoryDto categoryDto = categoryService.get(categoryId);
		logger.info(" category cover image : {}" + categoryDto.getCoverImage());
		InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}
}

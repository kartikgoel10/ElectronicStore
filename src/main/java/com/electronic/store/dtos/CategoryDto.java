package com.electronic.store.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private String categoryId;

	@NotBlank
	@Min(value = 4, message = "title length must be minimum of 4 characters ")
	private String title;

	@NotBlank(message = "description cannot be blank")
	private String description;

	@NotBlank(message = "cover image cannot be blank")
	private String coverImage;

}

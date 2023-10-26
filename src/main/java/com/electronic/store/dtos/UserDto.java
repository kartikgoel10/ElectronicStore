package com.electronic.store.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.electronic.store.validate.ImageNameValid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	private String userId;

	@Size(min = 3, max = 20, message = "Invalid Name")
	private String name;

	//@Email(message = "invalid email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" , message = "invalid email")
	@NotBlank(message = "email is required")
	private String email;

	@NotBlank(message = "password is required")
	private String password;

	@Size(min = 4 , max = 6 , message = "invalid gender")
	private String gender;

	@NotBlank(message = "about should not be blank")
	private String about;
	
	@ImageNameValid
	private String imageName;

}

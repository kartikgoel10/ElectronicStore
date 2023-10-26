package com.electronic.store.services;

import java.util.List;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;

public interface UserService {

	// create
	UserDto createUser(UserDto userDto);

	// update
	UserDto updateUser(UserDto userDto, String userId);

	// delete
	void deleteUser(String userId);

	// get all users
	PageableResponse<UserDto> getAllUsers(int pageNumber , int pageSize, String sortBy, String sortDir);

	// get users by id
	UserDto getUserById(String userId);

	// get user my email
	UserDto getUserByEmail(String email);

	// search users
	List<UserDto> searchUser(String keyword);

}

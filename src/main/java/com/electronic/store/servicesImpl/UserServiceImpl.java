package com.electronic.store.servicesImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entities.User;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto createUser(UserDto userDto) {

		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		User user = dtoToEntity(userDto);
		User savedUser = userRepository.save(user);
		UserDto newDto = entityToDto(savedUser);

		return newDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		User updatedUser = userRepository.save(user);
		UserDto updatedDto = entityToDto(updatedUser);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
		//delete profile image
		String fullPath = imageUploadPath + user.getImageName();
		try {
			Path path = Paths.get(fullPath);
			Files.delete(path);
		}catch(NoSuchFileException ex) {
			logger.info("no such file found");
			ex.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		userRepository.delete(user);

	}

	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> page = userRepository.findAll(pageable);
		PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
		return pageableResponse;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for given Id"));
		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user not found from given email"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}

	private UserDto entityToDto(User user) {

//		UserDto userDto = UserDto.builder().userId(user.getUserId()).name(user.getName()).email(user.getEmail())
//				.password(user.getPassword()).about(user.getAbout()).gender(user.getGender())
//				.imageName(user.getImageName()).build();

		return mapper.map(user, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
//		User user = User.builder().userId(userDto.getUserId()).name(userDto.getName()).email(userDto.getEmail())
//				.password(userDto.getPassword()).about(userDto.getAbout()).gender(userDto.getGender())
//				.imageName(userDto.getImageName()).build();

		return mapper.map(userDto, User.class);
	}

}

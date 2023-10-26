package com.electronic.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.electronic.store.helper.ApiResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// handling resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {

		logger.info("Exception Handler Invoked !!");
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(exception.getMessage()).success(true)
				.status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponseMessage>(responseMessage, HttpStatus.NOT_FOUND);
	}

	// method argumment not valid exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException exception) {
		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
		Map<String, Object> response = new HashMap();
		errors.stream().forEach(objectError -> {
			String message = objectError.getDefaultMessage();
			String field = ((FieldError) objectError).getField();
			response.put(field, message);
		});

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

	// Bad Api Request exception handler
	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> badApiRequestHandler(BadApiRequestException exception) {

		logger.info("Bad Api Request !!");
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(exception.getMessage())
				.success(false).status(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.BAD_REQUEST);
	}

}

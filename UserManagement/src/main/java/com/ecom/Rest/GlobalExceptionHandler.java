package com.ecom.Rest;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidAttributesException.class)
	public ResponseEntity<String> handleInvalidAttributesException(InvalidAttributesException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleGeneralException(Exception ex) {
//		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
}

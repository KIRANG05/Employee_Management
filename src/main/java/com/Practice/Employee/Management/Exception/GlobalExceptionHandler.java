package com.Practice.Employee.Management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Practice.Employee.Management.ResponseModal.GenericResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<GenericResponse> handleNotFound(EntityNotFoundException ex) {
		GenericResponse response = new GenericResponse(false, "Failed", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResponse> handleAll(Exception ex) {
		GenericResponse response = new GenericResponse(false, "Error", "Something went wrong");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

}

package com.Practice.Employee.Management.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Practice.Employee.Management.ResponseModal.GenericResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<GenericResponse> handleNotFound(EntityNotFoundException ex) {
		GenericResponse response = new GenericResponse(false, "Failed", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GenericResponse> handleAll(Exception ex, HttpServletRequest request) throws Exception {
		ex.printStackTrace();
		
		String path = request.getRequestURI();
		 if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || path.startsWith("/swagger-resources") || path.startsWith("/webjars")) {
		        throw ex; // Let Springdoc handle it
		    }
		GenericResponse response = new GenericResponse(false, "Error", "Something went wrong");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<GenericResponse> handleBadCredentials(BadCredentialsException ex) {
	    GenericResponse response = new GenericResponse(false, "Failed", "Invalid Username Or Password");
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<GenericResponse> handleUserNotFound(UsernameNotFoundException ex) {
	    GenericResponse response = new GenericResponse(false, "Failed", "User Not Found");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<GenericResponse> handleAccessDenied(AccessDeniedException ex) {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN)
	        .body(new GenericResponse(false, "Error", "Access Denied"));
	}

	@ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, SignatureException.class})
    public ResponseEntity<GenericResponse> handleJwtExceptions(Exception ex) {
		GenericResponse response = new GenericResponse(false, "Error", "Invalid or Expired Token");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error ->
	        errors.put(error.getField(), error.getDefaultMessage())
	    );
	    return ResponseEntity.badRequest().body(errors);
	}
	
	 // Handles validation errors from Hibernate (persist level)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv ->
            errors.put(cv.getPropertyPath().toString(), cv.getMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

}

package com.Practice.Employee.Management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;
import com.Practice.Employee.Management.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	@GetMapping("/hrList")
	public ResponseEntity<UserListResponse> getAllHrs(HttpServletRequest request){
		
		String operation = request.getRequestURI();
		Role role = Role.ROLE_HR;
		UserListResponse response = userService.getUsersByRole(role, operation);
		
		if(response != null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
			
		}else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	@GetMapping("/employeeList")
	public ResponseEntity<UserListResponse> getAllEmployees(HttpServletRequest request){
		
		String operation = request.getRequestURI();
		Role role = Role.ROLE_EMPLOYEE;
		UserListResponse response = userService.getUsersByRole(role, operation);
		
		if(response != null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
			
		}else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
		
	}

}

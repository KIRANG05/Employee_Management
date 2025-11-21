package com.Practice.Employee.Management.Controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.ChangePasswordRequest;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateRole/{id}")
	public ResponseEntity<GenericResponse> updateRole(@PathVariable Long id, @RequestParam String newRole, HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		
		Role roleEnum;
		
		 try {
		        roleEnum = Role.fromString(newRole); // convert manually using your JsonCreator method
		    } catch (IllegalArgumentException e) {
		        GenericResponse response = new GenericResponse();
		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage("Invalid role: " + newRole);
		        return ResponseEntity.badRequest().body(response);
		    }
		
		GenericResponse response = userService.updateRole(operation, id, roleEnum);
		
		if(response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);	
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/changePassword")
	public ResponseEntity<GenericResponse> changePassword(@RequestBody ChangePasswordRequest passwordRequest,Principal principal,  HttpServletRequest request){
		
		String operation = request.getRequestURI();
		
		GenericResponse response = userService.changePassword(passwordRequest, principal.getName(), operation);
		
		if(response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);	
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}
	}
	
	
	@GetMapping("/allUsers")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<UserListResponse> allUsers(HttpServletRequest request){
		
		String operation = request.getRequestURI();
		
		UserListResponse response = userService.allUsers(operation);
		
		if(response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);	
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}
		
	}

}

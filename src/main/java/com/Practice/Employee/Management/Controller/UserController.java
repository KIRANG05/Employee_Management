package com.Practice.Employee.Management.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.ChangePasswordRequest;
import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.ResponseModal.AdminUserResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;
import com.Practice.Employee.Management.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
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
	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
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
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
	@GetMapping("/admin/allUsers")
	public ResponseEntity<GenericResponse<List<AdminUserResponse>>> getAllUsers(
	        HttpServletRequest request) {

	    String operation = request.getRequestURI();

	    GenericResponse<List<AdminUserResponse>> response =
	    		userService.getAllUsersForAdmin(operation);

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

	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
	@GetMapping("/admin/{userId}")
	public ResponseEntity<GenericResponse<AdminUserResponse>> getUserForAdmin(
	        @PathVariable Long userId,
	        HttpServletRequest request) {

	    String operation = request.getRequestURI();
	    GenericResponse<AdminUserResponse> response =
	            userService.getUserForAdminById(userId, operation);

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
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
	@PutMapping("/userUpdateById/{id}")
	@Operation(
	        summary = "Update Employee By ID (Full Update)",
	        description = "Updates all employee fields by employee ID. Only accessible by ADMIN."
	    )
	public ResponseEntity<GenericResponse> updateEmployee(@RequestPart("employee") String employeeJson, @RequestPart(value = "image", required = false) MultipartFile image, @PathVariable Long id, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		
		String operation = request.getRequestURI();
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(employeeJson, Employee.class);
		
		GenericResponse response =  userService.updateById(employee, image, id, operation);
		if (response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
	@DeleteMapping("/{userId}")
	@Operation(
	        summary = "Delete User (and Employee if exists)",
	        description = "Deletes a user by userId. If an employee profile exists, it deletes both. ADMIN only."
	)
	public ResponseEntity<GenericResponse> deleteUserByUserId(
	        @PathVariable Long userId,
	        HttpServletRequest request
	) {

	    String operation = request.getRequestURI();
	    GenericResponse response = userService.deleteByUserId(userId, operation);

	    if (response.getIsSuccess()) {
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}



}

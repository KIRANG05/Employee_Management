package com.Practice.Employee.Management.Controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.AuthRequest;
import com.Practice.Employee.Management.Modal.TokenRefreshRequest;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserResponse;
import com.Practice.Employee.Management.Service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private AuthService authService;
	
	
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody Users user, HttpServletRequest request) {
		String operation = request.getRequestURI();
		
		UserResponse response = authService.registerUser(user, operation);
		
		if(response.getIsSuccess()) {
			return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
		}else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}

	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody AuthRequest authrequest, HttpServletRequest requset){
		String operation = requset.getRequestURI();
		
		UserResponse response =  authService.login(authrequest,operation);
		
		 if(response.getIsSuccess()) {
			return ResponseEntity
			 .status(HttpStatus.OK)
			 .body(response);
		 }else {
			return ResponseEntity
			 .status(HttpStatus.BAD_REQUEST)
			 .body(response);
		 }
		
		
		
		
	}
	
	 @PostMapping("/refresh")
	 public ResponseEntity<UserResponse> refresh(@RequestBody TokenRefreshRequest tokenRequest, HttpServletRequest request) {
		 
		 String operation = request.getRequestURI();
		 UserResponse response = authService.refreshAccessToken(tokenRequest.getRefreshToken(), operation);
		 
		 if (response.getIsSuccess()) {
			 return ResponseEntity
					 .status(HttpStatus.OK)
					 .body(response);
		 } else {
			 return ResponseEntity
					 .status(HttpStatus.BAD_REQUEST)
					 .body(response);
		 }
		 
	        
	    }

	    @PostMapping("/logout")
	    public ResponseEntity<GenericResponse> logout(@RequestBody TokenRefreshRequest logoutRequest, HttpServletRequest request) {
	    	
	    	String operation = request.getRequestURI();
	        GenericResponse response  = authService.logout(logoutRequest.getRefreshToken(), operation);
	       
	        if (response.getIsSuccess()) {
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

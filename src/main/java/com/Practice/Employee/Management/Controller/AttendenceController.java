package com.Practice.Employee.Management.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.ResponseModal.AttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.AttendenceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attendence")
public class AttendenceController {
	
	@Autowired
	AttendenceService attendenceService;

	
	@PostMapping("/punch-in")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GenericResponse<AttendenceResponse>> punchIn(Principal principal, HttpServletRequest request){
		
		String operation = request.getRequestURI();
		
		GenericResponse<AttendenceResponse> response = attendenceService.punchIn(principal.getName(), operation);
		
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
	
	@PostMapping("/punch-out")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GenericResponse<AttendenceResponse>> punchOut(Principal principal, HttpServletRequest request){
		
		String operation = request.getRequestURI();
		
		GenericResponse<AttendenceResponse> response = attendenceService.punchOut(principal.getName(), operation);
		
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

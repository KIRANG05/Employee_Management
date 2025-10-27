package com.Practice.Employee.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.ResponseModal.LeaveResponse;
import com.Practice.Employee.Management.Service.LeaveService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
	
	@Autowired
	LeaveService leaveService;
	
	@PostMapping("/apply/{employeeId}")
	public ResponseEntity<LeaveResponse> applyLeave(@PathVariable Long employeeId, @RequestBody LeaveRequest leaveRequest, HttpServletRequest request ){
		
		String operation = request.getRequestURI();
		
		LeaveResponse response  = leaveService.applyLeave(employeeId, leaveRequest, operation);
		
		if (response.getIsSuccess()) {
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(response);
			
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}
		
	}

}

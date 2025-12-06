package com.Practice.Employee.Management.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.Modal.LeaveStatus;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.LeaveResponse;
import com.Practice.Employee.Management.Service.LeaveService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
	
	@Autowired
	LeaveService leaveService;
	
	@PostMapping("/apply/{employeeId}")
	public ResponseEntity<GenericResponse<LeaveResponse>> applyLeave(@PathVariable Long employeeId, @RequestBody LeaveRequest leaveRequest, HttpServletRequest request ){
		
		String operation = request.getRequestURI();
		
		GenericResponse<LeaveResponse> response  = leaveService.applyLeave(employeeId, leaveRequest, operation);
		
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
	
	@GetMapping("/allLeaves")
	public ResponseEntity<GenericResponse<List<LeaveResponse>>> getAllLeaves(HttpServletRequest request){
		
		
		String operation = request.getRequestURI();
		GenericResponse<List<LeaveResponse>> response = leaveService.getAllLeaves(operation);
		
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
	
	@PutMapping("/status/{id}")
	public ResponseEntity<GenericResponse<LeaveResponse>> updateLeaveStatus(@PathVariable Long id, @RequestParam String action, HttpServletRequest request){
		
		
		String operation = request.getRequestURI();
		LeaveStatus newStatus;
		
		  if ("approve".equalsIgnoreCase(action)) {
	            newStatus = LeaveStatus.APPROVED;
	        } else if ("reject".equalsIgnoreCase(action)) {
	            newStatus = LeaveStatus.REJECTED;
	        } else {
	            GenericResponse<LeaveResponse> invalidResponse = new GenericResponse<>();
	            invalidResponse.setIsSuccess(false);
	            invalidResponse.setMessage("Invalid action");
	            return ResponseEntity.
	            		status(HttpStatus.BAD_REQUEST)
	            		.body(invalidResponse);
	        }
		
		GenericResponse<LeaveResponse> response = leaveService.updateLeaveStatus(id, newStatus, operation);
		
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
	
	@GetMapping("/summary/{employeeId}")
	public ResponseEntity<GenericResponse<List<LeaveResponse>>> getLeaveSummary(
	        @PathVariable Long employeeId,
	        HttpServletRequest request
	) {
	    String operation = request.getRequestURI();
	    GenericResponse<List<LeaveResponse>> response = leaveService.getLeaveSummary(employeeId, operation);

	    if (response.getIsSuccess()) {
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}


}

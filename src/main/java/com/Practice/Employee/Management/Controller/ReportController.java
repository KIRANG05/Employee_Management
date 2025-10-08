package com.Practice.Employee.Management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.ResponseModal.ReportResponse;
import com.Practice.Employee.Management.Service.ReportService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/reports")
@RestController
public class ReportController {
	
	
	private ReportService reportService;
	
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	@GetMapping("/taskSummary")
	public ResponseEntity<ReportResponse> getTaskSummary(HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		ReportResponse response = reportService.getTaskSummary(operation);
		
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
	
	@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
	@GetMapping("/taskSummaryByEmployee")
	public ResponseEntity<ReportResponse> getTaskSummaryByEmployee(@RequestParam String employeeName, HttpServletRequest requset){
		
		String operation = requset.getRequestURI();
		ReportResponse response = reportService.getTaskSummaryByEmployee(employeeName, operation);
		
		if(response.getIsSuccess()) {
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

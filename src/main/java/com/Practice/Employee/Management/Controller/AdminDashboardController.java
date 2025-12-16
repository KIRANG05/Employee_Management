package com.Practice.Employee.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.ResponseModal.AdminDashboardSummaryResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.AdminDashboardService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

	@Autowired
	private AdminDashboardService adminDashboardService;

	@GetMapping("/summary")
	public ResponseEntity<GenericResponse<AdminDashboardSummaryResponse>> getSummary(HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		
		GenericResponse<AdminDashboardSummaryResponse> response = adminDashboardService.getDashboardSummary(operation);

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

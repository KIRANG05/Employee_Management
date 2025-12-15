package com.Practice.Employee.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.HRDashboardSummaryResponse;
import com.Practice.Employee.Management.Service.HRDashboardService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hr/dashboard")
public class HRDashboardController {
	
	@Autowired
    private  HRDashboardService hrDashboardService;
    
   

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/summary")
    public ResponseEntity<GenericResponse<HRDashboardSummaryResponse>> getHRDashboardSummary(
            HttpServletRequest request) {

        String operation = request.getRequestURI();
        GenericResponse<HRDashboardSummaryResponse> response =
                hrDashboardService.getHRDashboardSummary(operation);
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

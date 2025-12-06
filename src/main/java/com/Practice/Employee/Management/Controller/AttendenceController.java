package com.Practice.Employee.Management.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.ResponseModal.AttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.EmpAttendenceResponse;
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
	
	@GetMapping("/myAttendence")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GenericResponse<List<AttendenceResponse>>> myAttendence(@RequestParam int year, @RequestParam int month, Principal principal, HttpServletRequest request){
		
		String operation = request.getRequestURI();
		
		GenericResponse<List<AttendenceResponse>> response = attendenceService.myAttendence(principal.getName(), year, month,  operation);
		
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
	
	@GetMapping("/admin/fetchAll")
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	public ResponseEntity<GenericResponse<List<AttendenceResponse>>> adminAttendence(@RequestParam int year, @RequestParam int month, @RequestParam Long employeeId, HttpServletRequest request){
		
		String operation = request.getRequestURI();
		
		GenericResponse<List<AttendenceResponse>> response = attendenceService.adminAttendence(employeeId, year, month,  operation);
		
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
	
	@GetMapping("/today")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GenericResponse<AttendenceResponse>> getToday(Principal principal, HttpServletRequest request){
		
		String Operation  = request.getRequestURI();
		GenericResponse<AttendenceResponse> response = attendenceService.getTodayStatus(Operation, principal.getName());
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
	
	@GetMapping("/today/{empId}")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<GenericResponse<AttendenceResponse>> getTodayForEmployee(
	        @PathVariable Long empId,
	        HttpServletRequest request) {

	    String operation = request.getRequestURI();
	    GenericResponse<AttendenceResponse> response = attendenceService.getTodayStatusByEmpId(operation, empId);

	    return response.getIsSuccess()
	            ? ResponseEntity.status(HttpStatus.OK).body(response)
	            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@GetMapping("/today/all")
	@PreAuthorize("hasAnyRole('ADMIN','HR')")
	public ResponseEntity<GenericResponse<List<EmpAttendenceResponse>>> getTodayAttendanceForAll(HttpServletRequest request) {

	    String operation = request.getRequestURI();
	    GenericResponse<List<EmpAttendenceResponse>> response = attendenceService.getAllTodayAttendance(operation);

	    return response.getIsSuccess()
	            ? ResponseEntity.status(HttpStatus.OK).body(response)
	            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	
	
}

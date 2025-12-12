package com.Practice.Employee.Management.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper; 

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
@SecurityRequirement(name = "BearerAuth")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/add", consumes = { "multipart/form-data" })
	 @Operation(
		        summary = "Add a New Employee",
		        description = "Creates a new employee in the system. Only accessible by users with ADMIN role."
		    )
	public ResponseEntity<EmployeeResponse> saveEmployee(@RequestPart("employee") String employeeJson, @RequestPart(value = "image", required = false) MultipartFile image, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		
		String operation = request.getRequestURI();
		
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(employeeJson, Employee.class);
		EmployeeResponse response = employeeService.save(employee, image,  operation);
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
		
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add/Bulk")
	 @Operation(
		        summary = "Add Multiple Employees",
		        description = "Adds multiple employees in a single request. Only accessible by users with ADMIN role."
		    )
	public ResponseEntity<EmployeeResponse> saveEmployees(@RequestBody List<Employee> employees, HttpServletRequest request) {
		String operation = request.getRequestURI();
		EmployeeResponse response = employeeService.saveAll(employees, operation);
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
	
	@PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
	@GetMapping("/employeeDetails")
	@Operation(
	        summary = "Get All Employees",
	        description = "Fetches the list of all employees. Accessible by ADMIN, HR, and EMPLOYEE roles."
	    )
	public ResponseEntity<EmployeeResponse> getAllEmployees(HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		EmployeeResponse response = employeeService.findAll(operation);
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
	
	@PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
	@GetMapping("/employeeDetails/{id}")
	@Operation(
	        summary = "Get Employee By ID",
	        description = "Fetches employee details using the employee ID. Accessible by ADMIN, HR, and EMPLOYEE roles."
	    )
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id, HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		EmployeeResponse response = employeeService.findById(id, operation);
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/employeeUpdateById/{id}")
	@Operation(
	        summary = "Update Employee By ID (Full Update)",
	        description = "Updates all employee fields by employee ID. Only accessible by ADMIN."
	    )
	public ResponseEntity<GenericResponse> updateEmployee(@RequestPart("employee") String employeeJson, @RequestPart(value = "image", required = false) MultipartFile image, @PathVariable Long id, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		
		String operation = request.getRequestURI();
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(employeeJson, Employee.class);
		
		GenericResponse response =  employeeService.updateById(employee, image, id, operation);
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/employeeUpdateById/{id}")
	 @Operation(
		        summary = "Partially Update Employee By ID",
		        description = "Partially updates employee fields by ID. Only accessible by ADMIN."
		    )
	public ResponseEntity<GenericResponse> partialUpdate(@RequestBody Employee employee, @PathVariable Long id, HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		GenericResponse response = employeeService.partialUpdateById(employee, id, operation);
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteById/{id}")
	@Operation(
	        summary = "Delete Employee By ID",
	        description = "Deletes the employee with the specified ID from the system. Only accessible by ADMIN."
	    )
	public ResponseEntity<GenericResponse> employeeDelete(@PathVariable Long id, HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		GenericResponse response = employeeService.deleteById(id, operation);
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
	
	@PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
	@GetMapping("/reportees")
//	@Operation(
//	        summary = "Get All Employees",
//	        description = "Fetches the list of all employees. Accessible by ADMIN, HR, and EMPLOYEE roles."
//	    )
	public ResponseEntity<EmployeeResponse> getAllReportees(HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		EmployeeResponse response = employeeService.findAllReportees(operation);
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

}

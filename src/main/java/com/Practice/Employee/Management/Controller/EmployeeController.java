package com.Practice.Employee.Management.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	
	
	@PostMapping("/add")
	public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody Employee employee, HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		EmployeeResponse response = employeeService.save(employee, operation);
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
		
	@PostMapping("/add/Bulk")
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
	
	@GetMapping("/employeeDetails")
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
	
	@GetMapping("/employeeDetails/{id}")
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
	
	@PutMapping("/employeeUpdateById/{id}")
	public ResponseEntity<GenericResponse> updateEmployee(@RequestBody Employee employee, @PathVariable Long id, HttpServletRequest request) {
		
		String operation = request.getRequestURI();
		GenericResponse response =  employeeService.updateById(employee, id, operation);
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
	
	@PatchMapping("/employeeUpdateById/{id}") 
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
	
	@DeleteMapping("/deleteById/{id}")
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

}

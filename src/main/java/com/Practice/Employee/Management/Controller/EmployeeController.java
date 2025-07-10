package com.Practice.Employee.Management.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public EmployeeResponse saveEmployee(@RequestBody Employee employee) {
		return employeeService.save(employee);
		
	}
		
	@PostMapping("/add/Bulk")
	public EmployeeResponse saveEmployees(@RequestBody List<Employee> employees) {
		return employeeService.saveAll(employees);
		
	}
	
	@GetMapping("/employeeDetails")
	public EmployeeResponse getAllEmployees() {
		return employeeService.findAll();
	}
	
	@GetMapping("/employeeDetails/{id}")
	public EmployeeResponse getEmployeeById(@PathVariable Long id) {
		return employeeService.findById(id);
	}
	
	@PutMapping("/employeeUpdateById/{id}")
	public GenericResponse updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
		return employeeService.updateById(employee, id);
	}
	
	@PatchMapping("/employeeUpdateById/{id}") 
	public GenericResponse partialUpdate(@RequestBody Employee employee, @PathVariable Long id) {
		return employeeService.partialUpdateById(employee, id);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public GenericResponse employeeDelete(@PathVariable Long id) {
		return employeeService.deleteById(id);
	}

}

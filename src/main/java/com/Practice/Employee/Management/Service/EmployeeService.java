package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;

public interface EmployeeService {

	EmployeeResponse save(Employee employee);

	EmployeeResponse saveAll(List<Employee> employees);

	EmployeeResponse findAll();

	EmployeeResponse findById(Long id);



}

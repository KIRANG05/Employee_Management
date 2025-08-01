package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface EmployeeService {

	EmployeeResponse save(Employee employee, String operation);

	EmployeeResponse saveAll(List<Employee> employees, String operation);

	EmployeeResponse findAll(String operation);

	EmployeeResponse findById(Long id, String operation);

	GenericResponse updateById(Employee employee, Long id, String operation);

	GenericResponse partialUpdateById(Employee employee, Long id, String operation);

	GenericResponse deleteById(Long id, String operation);



}

package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface EmployeeService {

	EmployeeResponse save(Employee employee);

	EmployeeResponse saveAll(List<Employee> employees);

	EmployeeResponse findAll();

	EmployeeResponse findById(Long id);

	GenericResponse updateById(Employee employee, Long id);

	GenericResponse partialUpdateById(Employee employee, Long id);

	GenericResponse deleteById(Long id);



}

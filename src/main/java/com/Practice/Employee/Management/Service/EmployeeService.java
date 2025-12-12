package com.Practice.Employee.Management.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface EmployeeService {

	EmployeeResponse save(Employee employee, MultipartFile image, String operation);

	EmployeeResponse saveAll(List<Employee> employees, String operation);

	EmployeeResponse findAll(String operation);

	EmployeeResponse findById(Long id, String operation);

	GenericResponse updateById(Employee employee, MultipartFile image, Long id, String operation);

	GenericResponse partialUpdateById(Employee employee, Long id, String operation);

	GenericResponse deleteById(Long id, String operation);

	EmployeeResponse findAllReportees(String operation);



}

package com.Practice.Employee.Management.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.EmployeeService;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ResponseCodeRespository responseCode;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public EmployeeResponse save(Employee employee) {
		Employee result=employeeRepository.save(employee);
		
		EmployeeResponse response = new EmployeeResponse();
		String operation = request.getRequestURI();
		
		if(result!= null) {
			String msg=responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setEmployee(result);
		}else {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_FAILED, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
		}
		return response;
	}

	@Override
	public EmployeeResponse saveAll(List<Employee> employees) {
		
		EmployeeResponse response = new EmployeeResponse();
		String operation = request.getRequestURI();
		
		List<Employee> result =  employeeRepository.saveAll(employees);
		
		if(result != null) {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_SUCCESS, operation);
			response.setStatus("Success");
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setEmployees(employees);
			
		}else {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_FAILED, operation);
			response.setStatus("Failed");
			response.setIsSuccess(false);
			response.setMessage(msg);
		}
		return response;
	}

	@Override
	public EmployeeResponse findAll() {
		
		EmployeeResponse response = new EmployeeResponse();
		String operation = request.getRequestURI();
		
		List<Employee> result = employeeRepository.findAll();
		
		if(result != null) {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setStatus("Success");
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setEmployees(result);
		}else {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setStatus("Failed");
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setEmployees(result);
		}

		return response;
	}

	@Override
	public EmployeeResponse findById(Long id) {
		
		EmployeeResponse response = new EmployeeResponse();
		String operation = request.getRequestURI();
		
		Optional<Employee> result = employeeRepository.findById(id);
		if(result.isPresent()) {
			Employee employee = result.get();
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setEmployee(employee);
		}else {
			String msg = responseCode.getMessageByCode(ResponseCode.NOT_FOUND, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
		}
		return response;
	}

	
	
	
	

}

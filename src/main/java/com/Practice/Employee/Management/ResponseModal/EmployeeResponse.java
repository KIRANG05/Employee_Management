package com.Practice.Employee.Management.ResponseModal;

import java.util.List;

import com.Practice.Employee.Management.Modal.Employee;

public class EmployeeResponse extends GenericResponse {
	
	
	private Employee employee;
	private List<Employee> employees;
	
	public EmployeeResponse() {
		super();
	}
	
	public EmployeeResponse(Boolean isSuccess, String status, String message, Employee employee,
			List<Employee> employees) {
		super(isSuccess, status, message);
		this.employee = employee;
		this.employees = employees;
	}
	
	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public List<Employee> getEmployees() {
		return employees;
	}


	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}

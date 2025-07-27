package com.Practice.Employee.Management.ServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.validation.constraints.AssertTrue;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;// this will create real onject of this class and inject all the mock beans inside this class means original class
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private ResponseCodeRespository responseCode;
	
	@Test
	void testEmployeeSaveSuccess() {
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Manoj");
		
		String opeartion = "CREATE";
		
		Mockito.when(employeeRepository.save(employee))
			   .thenReturn(employee);
		Mockito.when(responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_SUCCESS, opeartion))
			   .thenReturn("Employee Save Success");
		
		//This will call Actual Logic of save which is inside the ServiceImpl class then it will call repo but this will call modk repo that will return values 
		EmployeeResponse response = employeeService.save(employee, opeartion);
		
		assertTrue(response.getIsSuccess());
		assertEquals("Success",response.getStatus());
		assertEquals(1L, response.getEmployee().getId());
		assertNotNull(response.getEmployee());
		assertEquals("Employee Save Success", response.getMessage());
		
		verify(employeeRepository).save(employee);
		verify(responseCode).getMessageByCode(ResponseCode.EMPLOYEE_ADD_SUCCESS, opeartion);
		
	}
	
	@Test
	void testEmployeeSaveFailure() {
		
		Employee employee = new Employee();
		employee.setName("Sharavana");
		
		String operation = "CREATE";
		
		Mockito.when(employeeRepository.save(employee))
			   .thenReturn(null);
		Mockito.when(responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_FAILED, operation))
			   .thenReturn("Employee Save Failed");
		
		EmployeeResponse response = employeeService.save(employee, operation);
		assertFalse(response.getIsSuccess());
		assertNull(response.getEmployee());
		assertEquals("Failed", response.getStatus());
		assertEquals("Employee Save Failed", response.getMessage());
		
		verify(employeeRepository).save(employee);//verify(mock).method(args)
		verify(responseCode).getMessageByCode(ResponseCode.EMPLOYEE_ADD_FAILED, operation);
		
	}
	
	@Test
	void testEmployeeFindAllSuccess() {
		
		List<Employee> employee = new ArrayList<>();
		employee.add(new Employee());
		employee.add(new Employee());
		employee.add(new Employee());
		employee.add(new Employee());
		String operation = "FETCHALL";
		
		 Mockito.when(employeeRepository.findAll())
				.thenReturn(employee);
		 Mockito.when(responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation))
		 		.thenReturn("Employee Details Fetched Successfully");
		
		EmployeeResponse response = employeeService.findAll(operation);
		
		assertTrue(response.getIsSuccess());
		assertEquals("Success", response.getStatus());
		assertEquals("Employee Details Fetched Successfully", response.getMessage());
		assertNotNull(response.getEmployees());
		
		verify(employeeRepository).findAll();
		verify(responseCode).getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
		
		
	}

	
	@Test
	void testEmployeeFindAllfailure() {
		
		List<Employee> employee = new ArrayList<>();
		employee.add(new Employee());
		employee.add(new Employee());
		employee.add(new Employee());
		employee.add(new Employee());
		String operation = "FETCHALL";
		
		 Mockito.when(employeeRepository.findAll())
				.thenReturn(null);
		 Mockito.when(responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation))
		 		.thenReturn("Employee Details Fetched Failed");
		
		EmployeeResponse response = employeeService.findAll(operation);
		
		assertFalse(response.getIsSuccess());
		assertEquals("Failed", response.getStatus());
		assertEquals("Employee Details Fetched Failed", response.getMessage());
		assertNull(response.getEmployees());
		
		verify(employeeRepository).findAll();
		verify(responseCode).getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			
	}
	
	@Test
	void testEmployeeFindByIdSuccess( ) {
		
		Long id = 1L;
		
		Employee employee = new Employee();// 3 step
		employee.setId(1L);
		employee.setName("Kiran");
		String operation = "FindByid";
		
		
		Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));//2 step
		Mockito.when(responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation)).thenReturn("Success");//4 step
		
		EmployeeResponse response = employeeService.findById(id, operation);//1 step
		
		assertTrue(response.getIsSuccess());
		assertEquals("Success", response.getStatus());
		assertEquals("Success", response.getMessage());
		assertNotNull(response.getEmployee());
		
		verify(employeeRepository).findById(id);
		verify(responseCode).getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
		
		
		
	}
	
	@Test
	void testEmployeeFindByIdFailure( ) {
		
		Long id = 1L;
		String operation = "FindByid";
		
		
		Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.empty());//2 step
		Mockito.when(responseCode.getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation)).thenReturn("Data Not Found");//4 step
		
		EmployeeResponse response = employeeService.findById(id, operation);//1 step
		
		assertFalse(response.getIsSuccess());
		assertEquals("Failed", response.getStatus());
		assertEquals("Data Not Found", response.getMessage());
		assertNull(response.getEmployee());
		
		verify(employeeRepository).findById(id);
		verify(responseCode).getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation);
			
	}
	
	@Test
	void testEmployeeDeleteByIdSuccess() {
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Vignesh");
		
		
		Long id = 1L;
		String operation = "DeleteById";
		
		Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
		Mockito.when(responseCode.getMessageByCode(ResponseCode.EMPLOYEE_DELETE_SUCCESS, operation))
		.thenReturn("Employee Delete Success");
		
		GenericResponse response= employeeService.deleteById(id, operation);
		
		assertTrue(response.getIsSuccess());
		assertEquals("Success", response.getStatus());
		assertEquals("Employee Delete Success", response.getMessage());
		
		verify(employeeRepository).delete(employee);
		verify(responseCode).getMessageByCode(ResponseCode.EMPLOYEE_DELETE_SUCCESS, operation);
	}
	
	@Test
	void testEmployeeDeleteByIdFailure() {
		
		Long id = 1L;
		String operation = "DeleteById";
		
		Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.empty());
		Mockito.when(responseCode.getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation))
		.thenReturn("Employee Delete Failed");
		
		GenericResponse response= employeeService.deleteById(id, operation);
		
		assertFalse(response.getIsSuccess());
		assertEquals("Failed", response.getStatus());
		assertEquals("Employee Delete Failed", response.getMessage());
		
		verify(employeeRepository).findById(id);
		verify(responseCode).getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation);
		
		verify(employeeRepository, Mockito.never()).delete(Mockito.any());
	}
}

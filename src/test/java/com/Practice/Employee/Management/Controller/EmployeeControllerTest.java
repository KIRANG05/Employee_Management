package com.Practice.Employee.Management.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Security.CustomUserDetailsService;
import com.Practice.Employee.Management.Security.JwtAuthenticationFilter;
import com.Practice.Employee.Management.Security.JwtService;
import com.Practice.Employee.Management.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
	    controllers = EmployeeController.class,
	    excludeAutoConfiguration = {
	        SecurityAutoConfiguration.class,
	        UserDetailsServiceAutoConfiguration.class
	    }
	)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	
	@MockBean
	private JwtAuthenticationFilter jwtFilter;
	
	@Test
	void testSaveEmployeeSuccess() throws Exception { //In Junit5 no need to write Access Modifiers like public, private
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Vignesh");
		
		EmployeeResponse response = new EmployeeResponse();
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage("Employee Add Success");
		response.setEmployee(employee);
		
//		Mockito.when(employeeService.save(Mockito.any(Employee.class), Mockito.eq("/employee/add")))
//			   .thenReturn(response);
		
		Mockito.when(employeeService.save(
	            Mockito.any(Employee.class),
	            Mockito.any(MultipartFile.class),
	            Mockito.anyString()
	    )).thenReturn(response);
		
		String employeeJson = objectMapper.writeValueAsString(employee);
		
		  MockMultipartFile employeePart = new MockMultipartFile(
		            "employee",
		            "employee.json",
		            "application/json",
		            employeeJson.getBytes()
		    );
		  
		  MockMultipartFile imagePart = new MockMultipartFile(
		            "image",
		            "profile.png",
		            "image/png",
		            "dummy-image-content".getBytes()
		    );
		
		 mockMvc.perform(multipart("/employee/add")
				 .file(employeePart)
				 .file(imagePart)
		            .contentType(MediaType.MULTIPART_FORM_DATA)
		            .content(objectMapper.writeValueAsString(employee)))
		            .andExpect(status().isCreated())
		            .andExpect(jsonPath("$.isSuccess").value(true))
		            .andExpect(jsonPath("$.status").value("Success"))
		            .andExpect(jsonPath("$.message").value("Employee Add Success"))
		            .andExpect(jsonPath("$.employee.name").value("Vignesh"));
		
		
		
		
		
	}
	
	@Test
	void testSaveEmployeefailure() throws Exception {
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Vignesh");
		
		EmployeeResponse response = new EmployeeResponse();
		response.setIsSuccess(false);
		response.setStatus("Failed");
		response.setMessage("Employee Add Failed");
		response.setEmployee(null);
//		
//		Mockito.when(employeeService.save(Mockito.any(Employee.class), Mockito.eq("/employee/add")))
//			   .thenReturn(response);
		
		  Mockito.when(employeeService.save(
		            Mockito.any(Employee.class),
		            Mockito.any(MultipartFile.class),
		            Mockito.anyString()
		    )).thenReturn(response);
		  
		  String employeeJson = objectMapper.writeValueAsString(employee);

		    MockMultipartFile employeePart = new MockMultipartFile(
		            "employee",
		            "employee.json",
		            "application/json",
		            employeeJson.getBytes()
		    );

		    // Optional: mock an image file
		    MockMultipartFile imagePart = new MockMultipartFile(
		            "image",
		            "profile.png",
		            "image/png",
		            "dummy-image-content".getBytes()
		    );
		
		 mockMvc.perform(multipart("/employee/add")
				 .file(employeePart)
                 .file(imagePart)
		            .contentType(MediaType.MULTIPART_FORM_DATA)
		            .content(objectMapper.writeValueAsString(employee)))
		            .andExpect(status().isBadRequest())
		            .andExpect(jsonPath("$.isSuccess").value(false))
		            .andExpect(jsonPath("$.status").value("Failed"))
		            .andExpect(jsonPath("$.message").value("Employee Add Failed"))
		            .andExpect(jsonPath("$.employee").doesNotExist());	
		
	}
	
	@Test
	void testEmployeeFindAllSuccess() throws Exception {
		
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		
		
		EmployeeResponse response = new EmployeeResponse();
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setEmployees(employees);
		response.setMessage("Employee Details Fetch Success");
		
		
		Mockito.when(employeeService.findAll("/employee/employeeDetails")).thenReturn(response);
		
		mockMvc.perform(get("/employee/employeeDetails"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isSuccess").value(true))
			.andExpect(jsonPath("$.status").value("Success"))
			.andExpect(jsonPath("$.message").value("Employee Details Fetch Success"))
			.andExpect(jsonPath("$.employees").isArray())
			.andExpect(jsonPath("$.employees.length()").value(1));

	}

	
	@Test
	void testEmployeeFindAllFailure() throws Exception {
		
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		
		
		EmployeeResponse response = new EmployeeResponse();
		response.setIsSuccess(false);
		response.setStatus("Failed");
		response.setEmployees(null);
		response.setMessage("Employee Details Fetch Failed");
		
		
		Mockito.when(employeeService.findAll("/employee/employeeDetails")).thenReturn(response);
		
		mockMvc.perform(get("/employee/employeeDetails"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.isSuccess").value(false))
			.andExpect(jsonPath("$.status").value("Failed"))
			.andExpect(jsonPath("$.message").value("Employee Details Fetch Failed"))
			.andExpect(jsonPath("$.employees").doesNotExist());

	}
	
	@Test
	void testFindEmployeeByIdSuccess() throws Exception {
		
		Long id =1L;
		
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName("harsha");
		
		
		EmployeeResponse response = new EmployeeResponse();
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage("Success");
		response.setEmployee(employee);
		
		//we have to pass macthers in both arguments not sending raw value and matchers dont mix in single call means Mockito.eq(id) -> this is macther , id -> this is raw value 
		Mockito.when(employeeService.findById(Mockito.eq(id), Mockito.eq("/employee/employeeDetails/"+ id)))
				.thenReturn(response);
		
		mockMvc.perform(get("/employee/employeeDetails/{id}", id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isSuccess").value(true))
		.andExpect(jsonPath("$.status").value("Success"))
		.andExpect(jsonPath("$.message").value("Success"))
		.andExpect(jsonPath("$.employee.name").value("harsha"));
		
	}
	
	@Test
	void testFindEmployeeByIdFailure() throws Exception {
		
		Long id =1L;
		
		EmployeeResponse response = new EmployeeResponse();
		response.setIsSuccess(false);
		response.setStatus("Failed");
		response.setMessage("Data Not Found");
		response.setEmployee(null);
		
		//we have to pass macthers in both arguments not sending raw value and matchers dont mix in single call means Mockito.eq(id) -> this is macther , id -> this is raw value 
		Mockito.when(employeeService.findById(Mockito.eq(id), Mockito.eq("/employee/employeeDetails/"+ id)))
				.thenReturn(response);
		
		mockMvc.perform(get("/employee/employeeDetails/{id}", id))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.isSuccess").value(false))
		.andExpect(jsonPath("$.status").value("Failed"))
		.andExpect(jsonPath("$.message").value("Data Not Found"))
		.andExpect(jsonPath("$.employee").doesNotExist());
		
	}

	
	@Test
	void testEmployeeDeleteByIdSuccess() throws Exception { 
		
		
		Long id = 1L;
		
		GenericResponse response = new GenericResponse();
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage("Employee Delete Success");
		
		Mockito.when(employeeService.deleteById(Mockito.eq(id), Mockito.eq("/employee/deleteById/"+ id)))
		.thenReturn(response);
		
		mockMvc.perform(delete("/employee/deleteById/{id}",id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.isSuccess").value(true))
		.andExpect(jsonPath("$.status").value("Success"))
		.andExpect(jsonPath("$.message").value("Employee Delete Success"));
		
		
		
	}
	
	@Test
	void testEmployeeDeleteByIdFailure() throws Exception { 
		
		
		Long id = 1L;
		
		GenericResponse response = new GenericResponse();
		response.setIsSuccess(false);
		response.setStatus("Failed");
		response.setMessage("Employee Delete Failed");
		
		Mockito.when(employeeService.deleteById(Mockito.eq(id), Mockito.eq("/employee/deleteById/"+ id)))
		.thenReturn(response);
		
		mockMvc.perform(delete("/employee/deleteById/{id}",id))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.isSuccess").value(false))
		.andExpect(jsonPath("$.status").value("Failed"))
		.andExpect(jsonPath("$.message").value("Employee Delete Failed"));
		
		
		
	}


}

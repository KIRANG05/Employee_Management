package com.Practice.Employee.Management.ServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.EmployeeResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.EmployeeService;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	

	@Autowired
	private ResponseCodeRespository responseCode;
	
	private UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public EmployeeServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Value("${employee.images.path}")
	private String imageUploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Override
	public EmployeeResponse save(Employee employee,MultipartFile image, String operation) {
		logger.info("Save Operation Initiated For Employee: {}", employee);
		
		EmployeeResponse response = new EmployeeResponse();	
		
		try {
		if(image != null && !image.isEmpty()) {
			String fileName = UUID.randomUUID().toString() +"_"+ image.getOriginalFilename();
			Path filePath = Paths.get(imageUploadPath, fileName);
			Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			employee.setProfileImage(fileName);
		}
		
		Users user = new Users();
		user.setUsername(employee.getName().toLowerCase().replace(" ", "."));
		user.setPassword(passwordEncoder.encode("default123")); // default password
		user.setRole(Role.ROLE_EMPLOYEE); // default role
		userRepository.save(user);
		
		employee.setUser(user);

		
		Employee result = employeeRepository.save(employee);

		if (result != null) {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setEmployee(result);
			
			logger.info("Employee Saved Successfully With ID: {}", result.getId());
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_FAILED, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
			
			logger.error("Failed To Save Employee — Repository Did Not Return Entity. Name: {}", employee.getName());
		}
		}catch (Exception e) {
			 logger.error("Error while saving employee image: ", e);
		        response.setIsSuccess(false);
		        response.setMessage("Failed to upload image");
		        response.setStatus("Error");
		}
		return response;
	}

	@Override
	public EmployeeResponse saveAll(List<Employee> employees, String operation) {
		logger.info("Batch Save Operation Initiated — Total Employees To Save: {}. Operation: {}", employees.size(), operation);
		
		EmployeeResponse response = new EmployeeResponse();
		List<Employee> result = employeeRepository.saveAll(employees);

		if (result != null) {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_SUCCESS, operation);
			response.setStatus("Success");
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setEmployees(result);
			
			logger.info("Batch Save Successful — Saved {} Employees. Operation: {}", result.size(), operation);
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_ADD_FAILED, operation);
			response.setStatus("Failed");
			response.setIsSuccess(false);
			response.setMessage(msg);
			
			logger.error("Batch Save Failed — Repository Returned Null Or Empty. Operation: {}", operation);
		}
		return response;
	}

	@Override
	public EmployeeResponse findAll(String operation) {
		logger.info("FindAll Operation Initiated");
		
		EmployeeResponse response = new EmployeeResponse();
		List<Employee> result = employeeRepository.findAll();

		if (result != null) {
			
			  result.forEach(emp -> {
		            if (emp.getUser() != null) {
		                emp.setRole(emp.getUser().getRole()); // Assuming Employee has a 'role' field (transient or not persisted)
		            }
		        });
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setStatus("Success");
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setEmployees(result);
			
			logger.info("FindAll Successful — Total Employees Found: {}", result.size());
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setStatus("Failed");
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setEmployees(result);
			
			logger.error("FindAll Failed — No Employees Found. Operation: {}", operation);
		}
		return response;
	}

	@Override
	public EmployeeResponse findById(Long id, String operation) {
		logger.info("FindById Operation Initiated For ID: {}", id);
		
		EmployeeResponse response = new EmployeeResponse();
		Optional<Employee> result = employeeRepository.findById(id);//To Avoid Null pointer exception we use optional here means based on id the data may be present or may not be it store both (null or non null object)
		
		if (result.isPresent()) {
			Employee employee = result.get();
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setEmployee(employee);
			
			logger.info("Employee Found — ID: {}, Name: {}, Company: {}", 
	                 employee.getId(), employee.getName(), employee.getCompany());
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
			
			logger.error("Employee Not Found — ID: {}, Operation: {}", id, operation);
		}
		return response;
	}		

	@Override
	public GenericResponse updateById(Employee employee, MultipartFile image,  Long id, String operation) {
		logger.info("Update Operation Initiated For ID: {}", id);
		
		GenericResponse response = new GenericResponse();
		Optional<Employee> result = employeeRepository.findById(id);

		if (result.isPresent()) {
			
			Employee existingEmployee = result.get();
			existingEmployee.setName(employee.getName());
			existingEmployee.setCompany(employee.getCompany());
			existingEmployee.setSalary(employee.getSalary());
			
			
			if(!image.isEmpty() && image != null) {
				try {
	                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
	                
	                // Make sure this matches your property: employee.images.path=D:/Employee_Images/
	                Path imagePath = Paths.get(imageUploadPath + fileName);
	                
	                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
	                existingEmployee.setProfileImage(fileName);
	                
	                logger.info("New profile image uploaded for Employee ID: {}", id);
			} catch (IOException e) {
                logger.error("Error while saving image: {}", e.getMessage());
                response.setIsSuccess(false);
                response.setStatus("Failed");
                response.setMessage("Error saving image: " + e.getMessage());
			}
			}

			employeeRepository.save(existingEmployee);
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_UPDATE_SUCCESS, operation);
			response.setMessage(msg);
			response.setIsSuccess(true);
			response.setStatus("Success");
			
			logger.info("Employee Updated Successfully — ID: {}, Name: {}", employee.getId(), employee.getName());
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation);
			response.setMessage(msg);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			
			logger.error("Update Failed — Employee Not Found. ID: {}, Operation: {}", id, operation);
		}
		return response;
	}

	@Override
	public GenericResponse partialUpdateById(Employee employee, Long id, String operation) {
		logger.info("Partial Update Initiated For ID: {}", id);
		
		GenericResponse response = new GenericResponse();
		Optional<Employee> result = employeeRepository.findById(id);

		if (result.isPresent()) {

			Employee employeeV1 = result.get();

			if (employee.getName() != null) {
				employeeV1.setName(employee.getName());
			}
			if (employee.getCompany() != null) {
				employeeV1.setCompany(employee.getCompany());
			}
			if (employee.getSalary() != null) {
				employeeV1.setSalary(employee.getSalary());
			}
			employeeRepository.save(employeeV1);
			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_UPDATE_SUCCESS, operation);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setIsSuccess(true);
			
			logger.info("Partial Update Successful — ID: {}", id);
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation);
			response.setStatus("Failed");
			response.setMessage(msg);
			response.setIsSuccess(false);
			
			logger.error("Partial Update Failed — Employee Not Found. ID: {}, Operation: {}", id, operation);
		}
		return response;
	}

	@Override
	public GenericResponse deleteById(Long id, String operation) {
		logger.info("Delete Operation Initiated For Employee ID: {}", id);

		GenericResponse response = new GenericResponse();
		Optional<Employee> result = employeeRepository.findById(id);

		if (result.isPresent()) {

			Employee employee = result.get();
			employeeRepository.delete(employee);

			String msg = responseCode.getMessageByCode(ResponseCode.EMPLOYEE_DELETE_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			
			logger.info("Employee Deleted Successfully — ID: {}, Name: {}", employee.getId(), employee.getName());
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.DATA_NOT_FOUND, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
			
			logger.error("Delete Failed — Employee Not Found. ID: {}, Operation: {}", id, operation);
		}
		return response;
	}
	
}

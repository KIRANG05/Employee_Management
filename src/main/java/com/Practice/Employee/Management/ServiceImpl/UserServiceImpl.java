package com.Practice.Employee.Management.ServiceImpl;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.ChangePasswordRequest;
import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.AdminUserResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;
import com.Practice.Employee.Management.Service.NotificationService;
import com.Practice.Employee.Management.Service.UserService;
import com.Practice.Employee.Management.utils.NotificationMessage;
import com.Practice.Employee.Management.utils.NotificationType;
import com.Practice.Employee.Management.utils.ResponseCode;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	private ResponseCodeRespository responseCode;
	
	private final PasswordEncoder passwordEncoder;
	private NotificationService notificationService;
	private EmployeeRepository employeeRepository;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationService notificationService, EmployeeRepository employeeRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.notificationService = notificationService;
		this.employeeRepository = employeeRepository;
	}

	@Value("${employee.images.path}")
	private String imageUploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public UserListResponse getUsersByRole(Role role, String operation) {
	
		UserListResponse response = new UserListResponse();
		List<Users> result = userRepository.findUsersByRole(role);
		
		if(result != null && !result.isEmpty()) {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			List<String> userNames = new ArrayList<>();
			
			for (Users users : result) {
			 userNames.add(users.getUsername());
			
			}
			response.setUsernames(userNames);
		}else {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
			
		}
		
		return response;
	}
	
//	@Transactional
//	@Override
//	public GenericResponse updateRole(String operation, Long id, Role newRole) {
//		
//		GenericResponse response = new GenericResponse();
//		
////		Optional<Users> result = userRepository.findById(id);
//		try {
//		 Optional<Employee> opt = employeeRepository.findById(id);
//		    if (opt.isEmpty()) {
//		        response.setIsSuccess(false);
//		        response.setStatus("Failed");
//		        response.setMessage("Employee not found");
//		        return response;
//		    }
//		
//		    Employee emp = opt.get();
//		    
//		    Users user = emp.getUser();
//		    if (user == null) {
//	            String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_FAILED, operation);
//	            response.setIsSuccess(false);
//	            response.setStatus("Failed");
//	            response.setMessage(msg + " - User not mapped to this employee");
//	            return response;
//	        }
//		    user.setRole(newRole);
//	        userRepository.save(user);
//		
//		
//		String notificationMsg = NotificationMessage.ROLE_CHANGE_MESSAGE.replace("{role}", newRole.toString());
//		notificationService.sendNotification(user, NotificationType.ROLE_CHANGE, notificationMsg);
//		
//		String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_SUCCESS, operation);
//		response.setIsSuccess(true);
//		response.setMessage(msg);
//		response.setStatus("Success");
//		} catch (Exception e) {
//			String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_FAILED, operation);
//			response.setIsSuccess(false);
//			response.setMessage(msg);
//			response.setStatus("Failed");
//		} 
//			
//		
//		
//		return response;
//	}

	
	@Transactional
	@Override
	public GenericResponse updateRole(String operation, Long id, Role newRole) {
		
		GenericResponse response = new GenericResponse();
		
		
		try {
//		 Optional<Employee> opt = employeeRepository.findById(id);
			Optional<Users> result = userRepository.findById(id);
		    if (result.isEmpty()) {
		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage("User not found");
		        return response;
		    }
		
//		    Employee emp = opt.get();
		    
		    Users user = result.get();
		    if (user == null) {
	            String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_FAILED, operation);
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage(msg + " - User not mapped to this employee");
	            return response;
	        }
		    user.setRole(newRole);
	        userRepository.save(user);
		
		
		String notificationMsg = NotificationMessage.ROLE_CHANGE_MESSAGE.replace("{role}", newRole.toString());
		notificationService.sendNotification(user, NotificationType.ROLE_CHANGE, notificationMsg);
		
		String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setMessage(msg);
		response.setStatus("Success");
		} catch (Exception e) {
			String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_FAILED, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
		} 
			
		
		
		return response;
	}
	
	@Override
	public GenericResponse changePassword(ChangePasswordRequest passwordRequest, String username, String operation) {
	
		GenericResponse response = new GenericResponse();
		Optional<Users> result = userRepository.findByUsername(username);
		
		if(!result.isEmpty() || result != null) {
			
			Users user = result.get();
			if(!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
				
				response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage("Old password is incorrect");
			}
			
			user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
			userRepository.save(user);
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
			
		} else {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(msg);
		}
		
		return response;
	}

	@Override
	public UserListResponse allUsers(String operation) {
		
		UserListResponse response = new UserListResponse();
		
		List<Users> users = userRepository.findAll();
		
		if(!users.isEmpty() && users != null) {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setUsers(users);
		}else {
			String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
		}
		return response;
	}
	
	@Override
	public GenericResponse<List<AdminUserResponse>> getAllUsersForAdmin(String operation) {

	    GenericResponse<List<AdminUserResponse>> response = new GenericResponse<>();
	    try {
	    List<Users> users = userRepository.findAll();
	    List<Employee> employees = employeeRepository.findAll();

	    // Map userId -> Employee
	    Map<Long, Employee> employeeMap = employees.stream()
	            .filter(emp -> emp.getUser() != null)
	            .collect(Collectors.toMap(
	                    emp -> emp.getUser().getId(),
	                    emp -> emp
	            ));

	    List<AdminUserResponse> result = users.stream().map(user -> {

	        AdminUserResponse res = new AdminUserResponse();
	        res.setUserId(user.getId());
	        res.setUsername(user.getUsername());
	        res.setRole(user.getRole());

	        Employee emp = employeeMap.get(user.getId());

	        if (emp != null) {
	            res.setEmpId(emp.getId());
	            res.setName(emp.getName());
	            res.setCompany(emp.getCompany());
	            res.setSalary(emp.getSalary());
	            res.setProfileImage(emp.getProfileImage());
	            res.setHasEmployeeProfile(true);
	        } else {
	            res.setName("-");
	            res.setCompany("-");
	            res.setSalary(null);
	            res.setProfileImage(null);
	            res.setHasEmployeeProfile(false);// frontend show default
	        }

	        return res;
	    }).toList();
	    String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	    response.setIsSuccess(true);
	    response.setStatus("Success");
	    response.setMessage(msg);
	    response.setData(result);
	    } catch (Exception e) {
	    	 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
	 	    response.setIsSuccess(false);
	 	    response.setStatus("Failed");
	 	    response.setMessage(msg);
	 	    
		}

	    return response;
	}

	
	@Override
	public GenericResponse<AdminUserResponse> getUserForAdminById(Long userId, String operation) {

	    GenericResponse<AdminUserResponse> response = new GenericResponse<>();

	    try {
	        Users user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // Find employee by userId
	        Employee emp = employeeRepository.findByUser_Id(userId).orElse(null);

	        AdminUserResponse res = new AdminUserResponse();
	        res.setUserId(user.getId());
	        res.setUsername(user.getUsername());
	        res.setRole(user.getRole());

	        if (emp != null) {
	            res.setHasEmployeeProfile(true);
	            res.setEmpId(emp.getId());
	            res.setName(emp.getName());
	            res.setCompany(emp.getCompany());
	            res.setSalary(emp.getSalary());
	            res.setProfileImage(emp.getProfileImage());
	        } else {
	            res.setHasEmployeeProfile(false);
	            res.setEmpId(null);
	            res.setName(user.getUsername());
	            res.setCompany("-");
	            res.setSalary(null);
	            res.setProfileImage(null);
	        }
	        String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(res);

	    } catch (Exception e) {
	    	  String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(msg);
	        response.setData(null);
	    }

	    return response;
	}
	
	@Override
	public GenericResponse updateById(
	        Employee employee,
	        MultipartFile image,
	        Long userId,
	        String operation
	) {

	    GenericResponse response = new GenericResponse();

	    try {
	        /* ===============================
	           1️⃣ USER TABLE UPDATE
	           =============================== */
	        Optional<Users> userOpt = userRepository.findById(userId);
	        if (userOpt.isEmpty()) {
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage("User not found");
	            return response;
	        }

	        Users user = userOpt.get();

	        if (employee.getName() != null && !employee.getName().isBlank()) {
	            user.setUsername(employee.getName()); // always set, even if same
	            userRepository.save(user);
	            logger.info("Username updated for userId: {}", userId);
	        }

	        /* ===============================
	           2️⃣ EMPLOYEE TABLE UPDATE (IF EXISTS)
	           =============================== */
	        Optional<Employee> empOpt = employeeRepository.findByUser_Id(userId);

	        if (empOpt.isPresent()) {
	            Employee existingEmployee = empOpt.get();

	            // Always update fields
	            if (employee.getName() != null) existingEmployee.setName(employee.getName());
	            if (employee.getCompany() != null) existingEmployee.setCompany(employee.getCompany());
	            if (employee.getSalary() != null) existingEmployee.setSalary(employee.getSalary());

	            /* ===============================
	               IMAGE UPLOAD
	               =============================== */
	            if (image != null && !image.isEmpty()) {
	                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

	                Path uploadDir = Paths.get(imageUploadPath);
	                Files.createDirectories(uploadDir);

	                Path imagePath = uploadDir.resolve(fileName);
	                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

	                existingEmployee.setProfileImage(fileName);
	                logger.info("Profile image uploaded successfully: {}", fileName);
	            }

	            employeeRepository.save(existingEmployee);
	            logger.info("Employee updated for userId: {}", userId);

	        } else {
	            logger.info("No employee profile found for userId: {}", userId);
	        }

	        /* ===============================
	           3️⃣ FINAL RESPONSE
	           =============================== */
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(
	                responseCode.getMessageByCode(
	                        ResponseCode.USER_UPDATE_SUCCESS,
	                        operation
	                )
	        );
	        return response;

	    } catch (Exception e) {
	        logger.error("Update failed for userId: {}", userId, e);

	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(
	                responseCode.getMessageByCode(
	                        ResponseCode.USER_UPDATE_FAILED,
	                        operation
	                )
	        );
	        return response;
	    }
	}

	
	@Override
	@Transactional
	public GenericResponse deleteByUserId(Long userId, String operation) {

	    logger.info("Delete initiated for userId: {}", userId);
	    GenericResponse response = new GenericResponse();

	    try {
	        /* ===============================
	           1️⃣ CHECK USER EXISTS
	           =============================== */
	        Optional<Users> userOpt = userRepository.findById(userId);
	        if (userOpt.isEmpty()) {
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage(
	                    responseCode.getMessageByCode(
	                            ResponseCode.DATA_NOT_FOUND,
	                            operation
	                    )
	            );
	            return response;
	        }

	        Users user = userOpt.get();

	        /* ===============================
	           2️⃣ DELETE EMPLOYEE IF EXISTS
	           =============================== */
	        Optional<Employee> empOpt = employeeRepository.findByUser_Id(userId);
	        if (empOpt.isPresent()) {
	            Employee employee = empOpt.get();
	            employeeRepository.delete(employee);

	            logger.info(
	                    "Employee deleted for userId: {}, empId: {}",
	                    userId,
	                    employee.getId()
	            );
	        }

	        /* ===============================
	           3️⃣ DELETE USER
	           =============================== */
	        userRepository.delete(user);

	        logger.info("User deleted successfully — userId: {}", userId);

	        /* ===============================
	           4️⃣ RESPONSE
	           =============================== */
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(
	                responseCode.getMessageByCode(
	                        ResponseCode.USER_DELETE_SUCCESS,
	                        operation
	                )
	        );

	    } catch (Exception e) {
	        logger.error("Delete failed for userId: {}", userId, e);

	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(
	                responseCode.getMessageByCode(
	                        ResponseCode.USER_DELETE_FAILED,
	                        operation
	                )
	        );
	    }

	    return response;
	}





}

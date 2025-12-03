package com.Practice.Employee.Management.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.ChangePasswordRequest;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;
import com.Practice.Employee.Management.Service.NotificationService;
import com.Practice.Employee.Management.Service.UserService;
import com.Practice.Employee.Management.utils.NotificationMessage;
import com.Practice.Employee.Management.utils.NotificationType;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	private ResponseCodeRespository responseCode;
	
	private final PasswordEncoder passwordEncoder;
	private NotificationService notificationService;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationService notificationService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.notificationService = notificationService;
	}

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

	@Override
	public GenericResponse updateRole(String operation, Long id, Role newRole) {
		
		GenericResponse response = new GenericResponse();
		
		Optional<Users> result = userRepository.findById(id);
		
		if(!result.isEmpty() || result != null) {
		Users user = result.get();
		user.setRole(newRole);
		userRepository.save(user);
		
		String notificationMsg = NotificationMessage.ROLE_CHANGE_MESSAGE.replace("{role}", newRole.toString());
		notificationService.sendNotification(user, NotificationType.ROLE_CHANGE, notificationMsg);
		
		String msg = responseCode.getMessageByCode(ResponseCode.ROLE_UPDATE_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setMessage(msg);
		response.setStatus("Success");
		} else {
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

}

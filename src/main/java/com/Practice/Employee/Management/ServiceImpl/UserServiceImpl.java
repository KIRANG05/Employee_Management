package com.Practice.Employee.Management.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;
import com.Practice.Employee.Management.Service.UserService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	private ResponseCodeRespository responseCode;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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

}

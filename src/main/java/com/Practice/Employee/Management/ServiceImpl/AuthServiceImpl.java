package com.Practice.Employee.Management.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.UserResponse;
import com.Practice.Employee.Management.Service.AuthService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResponseCodeRespository responseCode;
	
	private final PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	

	@Override
	public UserResponse registerUser(Users user, String operation) {
		
		UserResponse response = new UserResponse();
		
		if(userRepository.findByUsername(user.getUsername()).isPresent()) {
			String msg = responseCode.getMessageByCode(ResponseCode.USERNAME_ALREADY_EXISTS, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
		}else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			Users result = userRepository.save(user);
			String msg = responseCode.getMessageByCode(ResponseCode.USER_REGISTER_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
		}
		return response;
	}

}

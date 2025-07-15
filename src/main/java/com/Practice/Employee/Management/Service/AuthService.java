package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.ResponseModal.UserResponse;

public interface AuthService {

	UserResponse registerUser(Users user, String operation);

}

package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.Modal.ChangePasswordRequest;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;

public interface UserService {

	UserListResponse getUsersByRole(Role role, String operation);

	GenericResponse updateRole(String operation, Long id, Role newRole);

	GenericResponse changePassword(ChangePasswordRequest passwordRequest, String username, String operation);

}

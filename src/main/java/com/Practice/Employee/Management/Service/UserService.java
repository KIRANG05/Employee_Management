package com.Practice.Employee.Management.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Practice.Employee.Management.Modal.ChangePasswordRequest;
import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.ResponseModal.AdminUserResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserListResponse;

public interface UserService {

	UserListResponse getUsersByRole(Role role, String operation);

	GenericResponse updateRole(String operation, Long id, Role newRole);

	GenericResponse changePassword(ChangePasswordRequest passwordRequest, String username, String operation);

	UserListResponse allUsers(String operation);

	GenericResponse<List<AdminUserResponse>> getAllUsersForAdmin(String operation);

	GenericResponse<AdminUserResponse> getUserForAdminById(Long userId, String operation);

	GenericResponse updateById(Employee employee, MultipartFile image, Long id, String operation);

	GenericResponse deleteByUserId(Long userId, String operation);

}

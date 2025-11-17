package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.LeaveResponse;

public interface LeaveService {

	GenericResponse<LeaveResponse> applyLeave(Long employeeId, LeaveRequest leaveRequest, String operation);

	GenericResponse<List<LeaveResponse>> getAllLeaves(String operation);

}

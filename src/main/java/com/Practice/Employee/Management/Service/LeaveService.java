package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.ResponseModal.LeaveResponse;

public interface LeaveService {

	LeaveResponse applyLeave(Long employeeId, LeaveRequest leaveRequest, String operation);

}

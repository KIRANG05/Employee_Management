package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.ResponseModal.AttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface AttendenceService {

	GenericResponse<AttendenceResponse> punchIn(String name, String operation);

	GenericResponse<AttendenceResponse> punchOut(String name, String operation);

}

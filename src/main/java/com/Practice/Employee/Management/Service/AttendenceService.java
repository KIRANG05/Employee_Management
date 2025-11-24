package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.ResponseModal.AttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface AttendenceService {

	GenericResponse<AttendenceResponse> punchIn(String name, String operation);

	GenericResponse<AttendenceResponse> punchOut(String name, String operation);

	GenericResponse<List<AttendenceResponse>> myAttendence(String name, int year, int month, String operation);

	GenericResponse<List<AttendenceResponse>> adminAttendence(Long employeeId, int year, int month, String operation);

	GenericResponse<AttendenceResponse> getTodayStatus(String operation, String name);

}

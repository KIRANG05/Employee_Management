package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.ResponseModal.AdminDashboardSummaryResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;

public interface AdminDashboardService {
	
	GenericResponse<AdminDashboardSummaryResponse> getDashboardSummary(String operation);

}

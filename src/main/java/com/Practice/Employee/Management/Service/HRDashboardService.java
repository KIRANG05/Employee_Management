package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.HRDashboardSummaryResponse;

public interface HRDashboardService {

	GenericResponse<HRDashboardSummaryResponse> getHRDashboardSummary(String operation);


}

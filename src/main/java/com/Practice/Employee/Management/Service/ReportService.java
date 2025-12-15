package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.ResponseModal.ReportResponse;

public interface ReportService {

	ReportResponse getTaskSummary(String operation);

	ReportResponse getTaskSummaryByEmployee(String employeeName, String operation);

	ReportResponse getTaskSummaryByHr(String hrName, String operation);

}

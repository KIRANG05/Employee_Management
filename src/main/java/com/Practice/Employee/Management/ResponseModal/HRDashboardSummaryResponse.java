package com.Practice.Employee.Management.ResponseModal;

public class HRDashboardSummaryResponse {
	
    private Long totalReportees;
    private Long pendingLeaveApprovals;
    private Long tasksAssignedToday;

    private ReportResponse taskSummary;

	public Long getTotalReportees() {
		return totalReportees;
	}

	public void setTotalReportees(Long totalReportees) {
		this.totalReportees = totalReportees;
	}

	public Long getPendingLeaveApprovals() {
		return pendingLeaveApprovals;
	}

	public void setPendingLeaveApprovals(Long pendingLeaveApprovals) {
		this.pendingLeaveApprovals = pendingLeaveApprovals;
	}

	public Long getTasksAssignedToday() {
		return tasksAssignedToday;
	}

	public void setTasksAssignedToday(Long tasksAssignedToday) {
		this.tasksAssignedToday = tasksAssignedToday;
	}

	public ReportResponse getTaskSummary() {
		return taskSummary;
	}

	public void setTaskSummary(ReportResponse taskSummary) {
		this.taskSummary = taskSummary;
	}

	@Override
	public String toString() {
		return "HRDashboardSummaryResponse [totalReportees=" + totalReportees + ", pendingLeaveApprovals="
				+ pendingLeaveApprovals + ", tasksAssignedToday=" + tasksAssignedToday + ", taskSummary=" + taskSummary
				+ "]";
	} 
    
    

}

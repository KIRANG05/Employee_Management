package com.Practice.Employee.Management.ResponseModal;

public class ReportResponse extends GenericResponse {
	
	private Long totalTasks;
	private Long completedTasks;
	private Long pendingTasks;
	private Long overDueTasks;
	private Long taskAssignedToday;
	
	public ReportResponse() {
		
	}

	public ReportResponse(Long totalTasks, Long completedTasks, Long pendingTasks, Long overDueTasks, Long taskAssignedToday) {
		this.totalTasks = totalTasks;
		this.completedTasks = completedTasks;
		this.pendingTasks = pendingTasks;
		this.overDueTasks = overDueTasks;
		this.taskAssignedToday = taskAssignedToday;
		
	}

	public Long getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(Long totalTasks) {
		this.totalTasks = totalTasks;
	}

	public Long getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(Long completedTasks) {
		this.completedTasks = completedTasks;
	}

	public Long getPendingTasks() {
		return pendingTasks;
	}

	public void setPendingTasks(Long pendingTasks) {
		this.pendingTasks = pendingTasks;
	}

	public Long getOverDueTasks() {
		return overDueTasks;
	}

	public void setOverDueTasks(Long overDueTasks) {
		this.overDueTasks = overDueTasks;
	}

	
	
	public Long getTaskAssignedToday() {
		return taskAssignedToday;
	}

	public void setTaskAssignedToday(Long taskAssignedToday) {
		this.taskAssignedToday = taskAssignedToday;
	}

	@Override
	public String toString() {
		return "ReportResponse [totalTasks=" + totalTasks + ", completedTasks=" + completedTasks + ", pendingTasks="
				+ pendingTasks + ", overDueTasks=" + overDueTasks + ", taskAssignedToday=" + taskAssignedToday + "]";
	}
	
	
}

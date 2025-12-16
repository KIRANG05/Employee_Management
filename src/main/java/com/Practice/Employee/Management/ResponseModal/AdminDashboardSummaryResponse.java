package com.Practice.Employee.Management.ResponseModal;

import java.util.Map;

public class AdminDashboardSummaryResponse {
	
	   private long totalEmployees;
	    private long totalHRs;
	    private long presentToday;
	    private long onLeaveToday;
	    private long activeTasks;
	    private long pendingLeaves;
	    private long approvedLeaves;
	    private Map<String, Long> roleCounts;
		public long getTotalEmployees() {
			return totalEmployees;
		}
		public void setTotalEmployees(long totalEmployees) {
			this.totalEmployees = totalEmployees;
		}
		public long getTotalHRs() {
			return totalHRs;
		}
		public void setTotalHRs(long totalHRs) {
			this.totalHRs = totalHRs;
		}
		public long getPresentToday() {
			return presentToday;
		}
		public void setPresentToday(long presentToday) {
			this.presentToday = presentToday;
		}
		public long getOnLeaveToday() {
			return onLeaveToday;
		}
		public void setOnLeaveToday(long onLeaveToday) {
			this.onLeaveToday = onLeaveToday;
		}
		public long getActiveTasks() {
			return activeTasks;
		}
		public void setActiveTasks(long activeTasks) {
			this.activeTasks = activeTasks;
		}
		public long getPendingLeaves() {
			return pendingLeaves;
		}
		public void setPendingLeaves(long pendingLeaves) {
			this.pendingLeaves = pendingLeaves;
		}
		
		
		public Map<String, Long> getRoleCounts() {
			return roleCounts;
		}
		public void setRoleCounts(Map<String, Long> roleCounts) {
			this.roleCounts = roleCounts;
		}
		
		
		public long getApprovedLeaves() {
			return approvedLeaves;
		}
		public void setApprovedLeaves(long approvedLeaves) {
			this.approvedLeaves = approvedLeaves;
		}
		@Override
		public String toString() {
			return "AdminDashboardSummaryResponse [totalEmployees=" + totalEmployees + ", totalHRs=" + totalHRs
					+ ", presentToday=" + presentToday + ", onLeaveToday=" + onLeaveToday + ", activeTasks="
					+ activeTasks + ", pendingLeaves=" + pendingLeaves + ", approvedLeaves=" + approvedLeaves
					+ ", roleCounts=" + roleCounts + "]";
		}
	    
	    

}

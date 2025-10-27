package com.Practice.Employee.Management.ResponseModal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.Modal.LeaveStatus;

public class LeaveResponse extends GenericResponse {
	
	 	private Long id;
	    private String employeeName;
	    private LocalDate fromDate;
	    private LocalDate toDate;
	    private String reason;
	    private LeaveStatus leaveStatus;
	    private LocalDateTime appliedAt;
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		public LocalDate getFromDate() {
			return fromDate;
		}
		public void setFromDate(LocalDate fromDate) {
			this.fromDate = fromDate;
		}
		public LocalDate getToDate() {
			return toDate;
		}
		public void setToDate(LocalDate toDate) {
			this.toDate = toDate;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public LeaveStatus getleaveStatus() {
			return leaveStatus;
		}
		public void setleaveStatuss(LeaveStatus leaveStatus) {
			this.leaveStatus = leaveStatus;
		}
		public LocalDateTime getAppliedAt() {
			return appliedAt;
		}
		public void setAppliedAt(LocalDateTime appliedAt) {
			this.appliedAt = appliedAt;
		}
		@Override
		public String toString() {
			return "LeaveResponse [id=" + id + ", employeeName=" + employeeName + ", fromDate=" + fromDate + ", toDate="
					+ toDate + ", reason=" + reason + ", leaveStatus=" + leaveStatus + ", appliedAt=" + appliedAt + "]";
		}

	

	

}

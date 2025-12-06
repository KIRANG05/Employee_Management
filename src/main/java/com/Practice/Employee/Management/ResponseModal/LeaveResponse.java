package com.Practice.Employee.Management.ResponseModal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.Modal.LeaveStatus;
import com.Practice.Employee.Management.Modal.LeaveType;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LeaveResponse {
	
	 	private Long id;
	    private String employeeName;
	    @JsonFormat(pattern = "dd-MM-yyyy")
	    private LocalDate fromDate;
	    @JsonFormat(pattern = "dd-MM-yyyy")
	    private LocalDate toDate;
	    private String reason;
	    private LeaveType leaveType;

	    private LeaveStatus leaveStatus;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
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
		public LeaveStatus getLeaveStatus() {
			return leaveStatus;
		}
		public void setLeaveStatus(LeaveStatus leaveStatus) {
			this.leaveStatus = leaveStatus;
		}
		public LocalDateTime getAppliedAt() {
			return appliedAt;
		}
		public void setAppliedAt(LocalDateTime appliedAt) {
			this.appliedAt = appliedAt;
		}
		
		public LeaveType getLeaveType() {
			return leaveType;
		}
		public void setLeaveType(LeaveType leaveType) {
			this.leaveType = leaveType;
		}
	
		@Override
		public String toString() {
			return "LeaveResponse [id=" + id + ", employeeName=" + employeeName + ", fromDate=" + fromDate + ", toDate="
					+ toDate + ", reason=" + reason + ", leaveType=" + leaveType + ", leaveStatus=" + leaveStatus
					+ ", appliedAt=" + appliedAt + "]";
		}

	

	

}

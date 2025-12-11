package com.Practice.Employee.Management.ResponseModal;

public class EmpAttendenceResponse extends AttendenceResponse {

	private Long userId;

	public Long getuserId() {
		return userId;
	}

	public void setuserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "EmpAttendenceResponse [userId=" + userId + "]";
	}

}

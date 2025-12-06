package com.Practice.Employee.Management.ResponseModal;

public class EmpAttendenceResponse extends AttendenceResponse {

	private Long empId;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "EmpAttendenceResponse [empId=" + empId + "]";
	}

}

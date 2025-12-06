package com.Practice.Employee.Management.ResponseModal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendenceResponse {

	
	private Long id;
	private String date;       
	private String status;     // P or A
	private String loginTime;  
	private String logoutTime;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	@Override
	public String toString() {
		return "AttendenceResponse [id=" + id + ", date=" + date + ", status=" + status + ", loginTime=" + loginTime
				+ ", logoutTime=" + logoutTime + "]";
	}
	
	
	
}

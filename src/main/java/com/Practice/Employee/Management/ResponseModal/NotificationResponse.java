package com.Practice.Employee.Management.ResponseModal;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotificationResponse {
		
	private Long id;
    private String type;
    private String message;
    
    private Long employeeId;
    private Long hrId;

    private Boolean sendToEmployee;
    private Boolean sendToHR;
    private Boolean sendToAdmin;
    
    private Boolean isRead;
//    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
	
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Long getHrId() {
		return hrId;
	}
	public void setHrId(Long hrId) {
		this.hrId = hrId;
	}
	public Boolean getSendToEmployee() {
		return sendToEmployee;
	}
	public void setSendToEmployee(Boolean sendToEmployee) {
		this.sendToEmployee = sendToEmployee;
	}
	public Boolean getSendToHR() {
		return sendToHR;
	}
	public void setSendToHR(Boolean sendToHR) {
		this.sendToHR = sendToHR;
	}
	public Boolean getSendToAdmin() {
		return sendToAdmin;
	}
	public void setSendToAdmin(Boolean sendToAdmin) {
		this.sendToAdmin = sendToAdmin;
	}
	@Override
	public String toString() {
		return "NotificationResponse [id=" + id + ", type=" + type + ", message=" + message + ", employeeId="
				+ employeeId + ", hrId=" + hrId + ", sendToEmployee=" + sendToEmployee + ", sendToHR=" + sendToHR
				+ ", sendToAdmin=" + sendToAdmin + ", isRead=" + isRead + ", createdAt=" + createdAt + "]";
	}
	
	
    
    

}

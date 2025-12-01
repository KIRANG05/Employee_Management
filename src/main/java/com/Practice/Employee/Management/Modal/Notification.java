package com.Practice.Employee.Management.Modal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private Long userId;
	private String type; 
	private String message; 
	
	private Long employeeId;  // specific employee (optional)
    private Long hrId;        // specific HR (optional)

    @Column(name = "send_to_employee")
    private Boolean sendToEmployee = false;
    @Column(name = "send_to_hr")
    private Boolean sendToHR = false;
    @Column(name = "send_to_admin")
    private Boolean sendToAdmin = false;
	
	private Boolean isRead = false;
	private LocalDateTime createdAt = LocalDateTime.now();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
	public String getType() {
		return type;
	}
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
	
	@Override
	public String toString() {
		return "Notification [id=" + id + ", type=" + type + ", message=" + message + ", employeeId=" + employeeId
				+ ", hrId=" + hrId + ", sendToEmployee=" + sendToEmployee + ", sendToHR=" + sendToHR + ", sendToAdmin="
				+ sendToAdmin + ", isRead=" + isRead + ", createdAt=" + createdAt + "]";
	}
	
	

}

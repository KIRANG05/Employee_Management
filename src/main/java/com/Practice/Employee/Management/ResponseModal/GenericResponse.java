package com.Practice.Employee.Management.ResponseModal;

public class GenericResponse {
	
	private Boolean isSuccess;
	private String status;
	private String message;
	
	public GenericResponse() { }
	
	public GenericResponse(Boolean isSuccess, String status, String message) {
		this.isSuccess = isSuccess;
		this.status = status;
		this.message = message;
	}
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "GenericResponse [isSuccess=" + isSuccess + ", status=" + status + ", message=" + message + "]";
	}
	
	

}

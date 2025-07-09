package com.Practice.Employee.Management.Modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Response_Messages")
public class ResponseMessages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String operation;
	private String message;
	
	public ResponseMessages() { }
	
	public ResponseMessages(Long id, String code, String operation, String message) {
		this.id = id;
		this.code = code;
		this.operation = operation;
		this.message = message;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "ResponseMessages [id=" + id + ", code=" + code + ", operation=" + operation + ", message=" + message
				+ "]";
	}
	
	
	
	

}

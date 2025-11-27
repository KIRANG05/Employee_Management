package com.Practice.Employee.Management.Websocket;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotificationEvent {

	private String type;
	private String message;
	private Long userId;   
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime time;
	private Object data; 

	public NotificationEvent() {

	}

	public NotificationEvent(String type, String message, Long userId,
			LocalDateTime time, Object data) {
		this.type = type;
		this.message = message;
		this.userId = userId;
		this.time = time;
		this.data = data;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NotificationEvent [type=" + type + ", message=" + message + ", userId=" + userId + ", time=" + time + ", data=" + data + "]";
	}


}

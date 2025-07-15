package com.Practice.Employee.Management.ResponseModal;

public class UserResponse extends GenericResponse {

	private String username;
	private String role;

	public UserResponse() {
	}

	public UserResponse(String username, String role) {

		this.username = username;
		this.role = role;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserResponse [username=" + username + ", role=" + role + "]";
	}
	
	

}

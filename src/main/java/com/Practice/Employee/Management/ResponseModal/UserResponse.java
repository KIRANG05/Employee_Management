package com.Practice.Employee.Management.ResponseModal;

public class UserResponse extends GenericResponse {

	private String username;
	private String role;
	private String accessToken;

	public UserResponse() {
	}

	public UserResponse(String username, String role, String accessToken) {

		this.username = username;
		this.role = role;
		this.accessToken = accessToken;

	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
		return "UserResponse [username=" + username + ", role=" + role + ", accessToken=" + accessToken + "]";
	}

	
	
	

}

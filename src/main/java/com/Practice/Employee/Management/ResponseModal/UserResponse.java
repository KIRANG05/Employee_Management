package com.Practice.Employee.Management.ResponseModal;

import java.util.List;

import com.Practice.Employee.Management.Modal.Users;

public class UserResponse extends GenericResponse {

	private Long id;
	private String username;
	private String role;
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	

	public UserResponse() {
	}

	public UserResponse(Long id, String username, String role, String accessToken, String refreshToken, String tokenType) {

		this.id = id;
		this.username = username;
		this.role = role;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
        this.tokenType = "Bearer";

	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenYype() {
		return tokenType;
	}

	public void setTokenYype(String tokenYype) {
		this.tokenType = tokenYype;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", username=" + username + ", role=" + role + ", accessToken=" + accessToken
				+ ", refreshToken=" + refreshToken + ", tokenType=" + tokenType + "]";
	}

	
	
	

}

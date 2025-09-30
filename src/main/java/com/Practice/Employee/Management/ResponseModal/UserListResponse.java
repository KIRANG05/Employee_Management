package com.Practice.Employee.Management.ResponseModal;

import java.util.List;

import com.Practice.Employee.Management.Modal.Users;

public class UserListResponse extends GenericResponse {
	
	
	private List<Users> users;
	private List<String> usernames;
	
	
	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public UserListResponse() {
		
	}
	
	public UserListResponse(List<Users> users) {
		this.users = users;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	

	@Override
	public String toString() {
		return "UserListResponse [users=" + users + ", usernames=" + usernames + "]";
	}
	
	

}

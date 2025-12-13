package com.Practice.Employee.Management.ResponseModal;

import com.Practice.Employee.Management.Modal.Role;

public class AdminUserResponse {
	
	 // user table fields
    private Long userId;
    private String username;
    private Role role;

    // employee table fields (nullable)
    private Long empId;
    private String name;
    private String company;
    private Double salary;
    private String profileImage;
    
    private boolean hasEmployeeProfile;
	public boolean isHasEmployeeProfile() {
		return hasEmployeeProfile;
	}
	public void setHasEmployeeProfile(boolean hasEmployeeProfile) {
		this.hasEmployeeProfile = hasEmployeeProfile;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	@Override
	public String toString() {
		return "AdminUserResponse [userId=" + userId + ", username=" + username + ", role=" + role + ", empId=" + empId
				+ ", name=" + name + ", company=" + company + ", salary=" + salary + ", profileImage=" + profileImage
				+ ", hasEmployeeProfile=" + hasEmployeeProfile + "]";
	}
    
    

}

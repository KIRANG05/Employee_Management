package com.Practice.Employee.Management.Modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_details")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	@NotEmpty(message = "* It is Required Field")
	@Size(min =5, max =15, message = "* Enter Between 5~15 Charecters")
	private String username;
	@Column(nullable = false)
	@NotEmpty(message = "* It is Required")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$", message = "* Enter minimum 8 charecter, one uppercase, one lowercase, one number and one special charecter")
	private String password;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "* It is Required")
	private Role role;

	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Employee employee;

	
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", employee=" + employee + "]";
	}

}

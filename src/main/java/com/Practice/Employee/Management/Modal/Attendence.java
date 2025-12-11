package com.Practice.Employee.Management.Modal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendence")
public class Attendence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	private LocalDate date;
	private LocalDateTime loginTime;
	private LocalDateTime logoutTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalDateTime getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}
	public LocalDateTime getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(LocalDateTime logoutTime) {
		this.logoutTime = logoutTime;
	}
	@Override
	public String toString() {
		return "Attendence [id=" + id + ", user=" + user + ", date=" + date + ", loginTime=" + loginTime
				+ ", logoutTime=" + logoutTime + "]";
	}
	
	

}

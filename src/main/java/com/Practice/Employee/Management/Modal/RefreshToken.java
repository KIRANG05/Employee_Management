package com.Practice.Employee.Management.Modal;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Refresh_Tokens")
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String token;

	@Column(nullable = false)
	private Instant expiryDate;

	@Column(nullable = false, updatable = false)
	private Instant createdAt = Instant.now(); // audit field

	@Column(nullable = false)
	private Boolean isDeleted = false;

	public RefreshToken() {
	}

	public RefreshToken(String username, String token, Instant expiryDate) {
		this.username = username;
		this.token = token;
		this.expiryDate = expiryDate;
		this.createdAt = Instant.now();
		this.isDeleted = false;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "RefreshToken [id=" + id + ", username=" + username + ", token=" + token + ", expiryDate=" + expiryDate
				+ ", createdAt=" + createdAt + ", isDeleted=" + isDeleted + "]";
	}

}

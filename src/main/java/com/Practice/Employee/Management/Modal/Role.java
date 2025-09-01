package com.Practice.Employee.Management.Modal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

	ROLE_ADMIN,
	ROLE_HR,
	ROLE_EMPLOYEE;
	
	 @JsonCreator
	    public static Role fromString(String value) {
	        return switch (value.toUpperCase()) {
	            case "ADMIN", "ROLE_ADMIN" -> ROLE_ADMIN;
	            case "HR", "ROLE_HR" -> ROLE_HR;
	            case "EMPLOYEE", "ROLE_EMPLOYEE" -> ROLE_EMPLOYEE;
	            default -> throw new IllegalArgumentException("Unknown role: " + value);
	        };
	    }

	    @JsonValue
	    public String toJson() {
	        return this.name().replace("ROLE_", ""); // e.g., "ROLE_ADMIN" → "ADMIN"
	    }
}

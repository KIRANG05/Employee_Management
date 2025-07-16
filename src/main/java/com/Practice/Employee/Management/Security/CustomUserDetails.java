package com.Practice.Employee.Management.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Practice.Employee.Management.Modal.Users;

public class CustomUserDetails implements UserDetails {

	private final Users user;

	public CustomUserDetails(Users user) {
		this.user = user;
	}

	public Users getUser() {  // 👉 Add this method
        return user;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // or implement logic based on your app
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // or use a user.isLocked() flag
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // for example, if passwords expire after 90 days
	}

	@Override
	public boolean isEnabled() {
		return true; // or use user.isEnabled() if stored in DB
	}

}

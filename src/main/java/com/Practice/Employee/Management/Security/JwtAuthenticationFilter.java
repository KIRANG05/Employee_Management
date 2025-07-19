package com.Practice.Employee.Management.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private JwtService jwtService;
	private CustomUserDetailsService userDetailsService;
	
	
	public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			 filterChain.doFilter(request, response);
			 return;
		}
		
		 token = authHeader.substring(7);
		 username = jwtService.extractUserName(token);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
			
			if (jwtService.isTokenValid(token, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}

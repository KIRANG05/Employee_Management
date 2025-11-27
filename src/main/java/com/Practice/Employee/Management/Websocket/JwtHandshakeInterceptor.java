package com.Practice.Employee.Management.Websocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.Practice.Employee.Management.Security.JwtService;


@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
	
	@Autowired
    private JwtService jwtService;	

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		String path = request.getURI().getPath();
	    if (path.endsWith("/info")) {
	        return true; // Allow SockJS /info request
	    }
		
		String query = request.getURI().getQuery();  // token=xxxx
        if (query == null || !query.contains("token=")) {
            return false; // Reject connection
        }

        String token = query.split("token=")[1];

        String username = jwtService.extractUserName(token);
        if (!jwtService.isTokenValid(token, username)) {
            return false;
        }

        // Store user info in session for backend to identify
        attributes.put("username", username);

        return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}
	

}

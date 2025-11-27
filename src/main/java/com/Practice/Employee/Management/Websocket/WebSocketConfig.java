package com.Practice.Employee.Management.Websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	private JwtHandshakeInterceptor jwtHandshakeInterceptor;
	
		@Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/emp/notification/ws")
	        .addInterceptors(jwtHandshakeInterceptor)
	                .setAllowedOriginPatterns("*")
	                .withSockJS();
	                  // auto-reconnect support
	    }
		
		 	@Override
		    public void configureMessageBroker(MessageBrokerRegistry registry) {
		        // This is where frontend listens
		        registry.enableSimpleBroker("/topic");

		        // This is where frontend sends messages (if needed)
		        registry.setApplicationDestinationPrefixes("/app");
		    }

		 	

		 	
}

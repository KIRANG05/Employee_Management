package com.Practice.Employee.Management.Websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.Practice.Employee.Management.ResponseModal.NotificationResponse;

@Component
public class NotificationWebSocketSender {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	public NotificationWebSocketSender(SimpMessagingTemplate simpMessagingTemplate ) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	
	 // Send message to a specific user
    public void sendToUser(Long userId, NotificationResponse event) {
    	simpMessagingTemplate.convertAndSend("/topic/user/" + userId, event);
    }

 // GLOBAL notification – all users
    public void sendGlobal(NotificationResponse event) {
    	simpMessagingTemplate.convertAndSend("/topic/global", event);
    }
    
    public void sendToAdminDashboard(NotificationResponse event) {
    	simpMessagingTemplate.convertAndSend("/topic/admin-dashboard", event);
    }
    
}

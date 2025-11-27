package com.Practice.Employee.Management.Websocket;

import java.time.LocalDateTime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class WebSocketTestController {
	
	private final NotificationWebSocketSender sender;
	
	public WebSocketTestController(NotificationWebSocketSender sender ) {
		this.sender = sender;
	}
	
	@GetMapping("/user/{userId}")
	public String testUser(@PathVariable Long userId) {
		NotificationEvent event = new NotificationEvent(
				"TEST",
				"Hello User " + userId,
				userId,
				LocalDateTime.now(),
				null
				);

		sender.sendToUser(userId, event);
		return "Sent to user " + userId;
	}
	


}

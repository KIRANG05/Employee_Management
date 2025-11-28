package com.Practice.Employee.Management.ServiceImpl;

import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.Repository.NotificationRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.NotificationResponse;
import com.Practice.Employee.Management.Service.NotificationService;
import com.Practice.Employee.Management.Websocket.NotificationWebSocketSender;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	private NotificationRepository notificationRepository;
	private NotificationWebSocketSender notificationWebSocketSender;
	
	public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationWebSocketSender notificationWebSocketSender) {
		this.notificationRepository = notificationRepository;
		this.notificationWebSocketSender = notificationWebSocketSender;
	}

	@Override
	public GenericResponse<NotificationResponse> saveNotification(Notification notification, String operation) {
		
		GenericResponse<NotificationResponse> response = new GenericResponse<>();
		 NotificationResponse notificationResponse = new NotificationResponse();
		
		 Notification result = notificationRepository.save(notification);
		 
		 notificationResponse.setId(result.getId());
		 notificationResponse.setType(result.getType());
		 notificationResponse.setMessage(result.getMessage());
		 notificationResponse.setIsRead(result.getIsRead());
		 notificationResponse.setCreatedAt(result.getCreatedAt());
		 
		 notificationWebSocketSender.sendToUser(result.getUserId(), notificationResponse);
		 
		
		 
		 
		 
		
		
		
		return null;
	}

}

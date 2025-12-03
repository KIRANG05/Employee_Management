package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.NotificationResponse;
import com.Practice.Employee.Management.ResponseModal.PagedResponse;

public interface NotificationService {
	void sendNotification(Users user, String type, String message);

	GenericResponse<NotificationResponse> saveNotification(Notification notification);

//	GenericResponse<List<NotificationResponse>> getUserNotifications(Long userId, String operation);

	GenericResponse<String> markNotificationAsRead(Long notificationId, String operation);

	GenericResponse<PagedResponse<NotificationResponse>> getNotificationsWithPagination(Long userId, String role, int page, int size,
			String operation);

//	GenericResponse<String> markAllAsReadByUsername(String username, String operation);

	GenericResponse<String> markAllAsReadByRole(Long userId, String role, String operation);

}

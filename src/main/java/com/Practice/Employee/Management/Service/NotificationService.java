package com.Practice.Employee.Management.Service;

import java.util.List;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.NotificationResponse;
import com.Practice.Employee.Management.ResponseModal.PagedResponse;

public interface NotificationService {

	GenericResponse<NotificationResponse> saveNotification(Notification notification, Boolean sendToAdminAlso);

	GenericResponse<List<NotificationResponse>> getUserNotifications(Long userId, String operation);

	GenericResponse<String> markNotificationAsRead(Long notificationId, String operation);

	GenericResponse<PagedResponse<NotificationResponse>> getNotificationsWithPagination(Long userId, int page, int size,
			String operation);

}

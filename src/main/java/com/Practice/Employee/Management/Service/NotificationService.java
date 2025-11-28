package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.NotificationResponse;

public interface NotificationService {

	GenericResponse<NotificationResponse> saveNotification(Notification notification, String operation);

}

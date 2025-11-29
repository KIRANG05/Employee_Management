package com.Practice.Employee.Management.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.NotificationResponse;
import com.Practice.Employee.Management.ResponseModal.PagedResponse;
import com.Practice.Employee.Management.Service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	


    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GenericResponse<NotificationResponse>> createNotification(
            @RequestBody Notification notification,
            Boolean sendToAdminAlso
           ) {

       
        GenericResponse<NotificationResponse> response = notificationService.saveNotification(notification, sendToAdminAlso);

        if (response.getIsSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @GetMapping("/fetch")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GenericResponse<List<NotificationResponse>>> getUserNotifications(
            @RequestParam Long userId,
            HttpServletRequest request) {

        String operation = request.getRequestURI();
        GenericResponse<List<NotificationResponse>> response = notificationService.getUserNotifications(userId, operation);

        if (response.getIsSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PutMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GenericResponse<String>> markAsRead(
            @RequestParam Long notificationId,
            HttpServletRequest request) {
    	
    	String operation = request.getRequestURI();

        GenericResponse<String> response = notificationService.markNotificationAsRead(notificationId, operation);

        if (response.getIsSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/fetch-paged")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GenericResponse<PagedResponse<NotificationResponse>>> fetchPaged(
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam int size,
            HttpServletRequest request) {
    	
    	String operation = request.getRequestURI();

        GenericResponse<PagedResponse<NotificationResponse>> response =
                notificationService.getNotificationsWithPagination(userId, page, size, operation);

        if (response.getIsSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }



}

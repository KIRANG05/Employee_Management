package com.Practice.Employee.Management.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.Repository.NotificationRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.NotificationResponse;
import com.Practice.Employee.Management.ResponseModal.PagedResponse;
import com.Practice.Employee.Management.Service.NotificationService;
import com.Practice.Employee.Management.Websocket.NotificationWebSocketSender;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	private NotificationRepository notificationRepository;
	private NotificationWebSocketSender notificationWebSocketSender;
	private ResponseCodeRespository resposeCode;
    private HttpServletRequest request; 
	
	public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationWebSocketSender notificationWebSocketSender, HttpServletRequest request) {
		this.notificationRepository = notificationRepository;
		this.notificationWebSocketSender = notificationWebSocketSender;
		this.request = request;
	}

	@Override
	public GenericResponse<NotificationResponse> saveNotification(Notification notification, Boolean sendToAdminAlso) {
		
		GenericResponse<NotificationResponse> response = new GenericResponse<>();
		 NotificationResponse notificationResponse = new NotificationResponse();
		 String operation = request.getRequestURI();
		 try {
			 Notification result = notificationRepository.save(notification);
				
			 notificationResponse.setId(result.getId());
			 notificationResponse.setType(result.getType());
			 notificationResponse.setMessage(result.getMessage());
			 notificationResponse.setIsRead(result.getIsRead());
			 notificationResponse.setCreatedAt(result.getCreatedAt());
			 notificationResponse.setUserId(result.getUserId());
			 
			 notificationWebSocketSender.sendToUser(result.getUserId(), notificationResponse);
			 
			 if (Boolean.TRUE.equals(sendToAdminAlso)) {
				 notificationWebSocketSender.sendToAdminDashboard(notificationResponse);
			    }
			 
			 String msg = resposeCode.getMessageByCode(ResponseCode.NOTIFICATION_SENT_SUCCESS, operation);
			 response.setIsSuccess(true);
			 response.setMessage(msg);
			 response.setStatus("Success");
			 response.setData(notificationResponse); 
		 } catch (Exception e) {
			 String msg = resposeCode.getMessageByCode(ResponseCode.NOTIFICATION_SENT_SUCCESS, operation);
			 response.setIsSuccess(false);
			 response.setMessage(msg);
			 response.setStatus("Failed");
			 response.setData(null); 
		}
		return response;
	}
	
	@Override
	public GenericResponse<List<NotificationResponse>> getUserNotifications(Long userId, String operation) {

	    GenericResponse<List<NotificationResponse>> response = new GenericResponse<>();

	    try {
	        List<Notification> list = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);

	        List<NotificationResponse> responseList = new ArrayList<NotificationResponse>();

	        for (Notification notification : list) {
	            NotificationResponse notificationResponse = new NotificationResponse();
	            notificationResponse.setId(notification.getId());
	            notificationResponse.setType(notification.getType());
	            notificationResponse.setMessage(notification.getMessage());
	            notificationResponse.setIsRead(notification.getIsRead());
	            notificationResponse.setCreatedAt(notification.getCreatedAt()); // or formatted date if needed
	            notificationResponse.setUserId(notification.getUserId());
	            
	            responseList.add(notificationResponse);
	        }

	        String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(responseList);

	    } catch (Exception e) {
	        String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(msg);
	        response.setData(null);
	    }

	    return response;
	}
	
	@Override
	public GenericResponse<String> markNotificationAsRead(Long notificationId, String operation) {

	    GenericResponse<String> response = new GenericResponse<>();

	    Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);

	    if (notificationOpt.isEmpty()) {
	    	String msg = resposeCode.getMessageByCode(ResponseCode.NOTIFICATION_NOT_FOUND, operation);
	        response.setIsSuccess(false);
	        response.setMessage(msg);
	        response.setStatus("Failed");
	        return response;
	    }

	    Notification notification = notificationOpt.get();
	    notification.setIsRead(true);
	    notificationRepository.save(notification);

	    String msg = resposeCode.getMessageByCode(ResponseCode.NOTIFICATION_MARKED_AS_READ, operation);
	    response.setIsSuccess(true);
	    response.setMessage(msg);
	    response.setStatus("Success");
	    return response;
	}

	@Override
	public GenericResponse<PagedResponse<NotificationResponse>> getNotificationsWithPagination(Long userId, int page,
			int size, String operation) {
		 GenericResponse<PagedResponse<NotificationResponse>> response = new GenericResponse<>();

		 try {
		    Pageable pageable = PageRequest.of(page, size);
		    Page<Notification> resultPage = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

		    List<NotificationResponse> list = new ArrayList<NotificationResponse>();

		    for (Notification notification : resultPage.getContent()) {
		        NotificationResponse notificationResponse = new NotificationResponse();
		        notificationResponse.setId(notification.getId());
		        notificationResponse.setUserId(notification.getUserId());
		        notificationResponse.setType(notification.getType());
		        notificationResponse.setMessage(notification.getMessage());
		        notificationResponse.setIsRead(notification.getIsRead());
		        notificationResponse.setCreatedAt(notification.getCreatedAt());
		        list.add(notificationResponse);
		    }

		    PagedResponse<NotificationResponse> paged = new PagedResponse<>();
		    paged.setContent(list);
		    paged.setPageNumber(resultPage.getNumber());
		    paged.setPageSize(resultPage.getSize());
		    paged.setTotalPages(resultPage.getTotalPages());
		    paged.setTotalElements(resultPage.getTotalElements());

		    String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
		    response.setIsSuccess(true);
		    response.setStatus("Success");
		    response.setMessage(msg);
		    response.setData(paged);
		 } catch (Exception e) {
			 String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			    response.setIsSuccess(false);
			    response.setStatus("Failed");
			    response.setMessage(msg);
			    response.setData(null);
		}

		    return response;
	}



}

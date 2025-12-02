package com.Practice.Employee.Management.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.NotificationRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
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
    private UserRepository userRepository;
	
	public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationWebSocketSender notificationWebSocketSender, HttpServletRequest request,
			ResponseCodeRespository resposeCode, UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.notificationWebSocketSender = notificationWebSocketSender;
		this.request = request;
		this.resposeCode = resposeCode;
		this.userRepository = userRepository;
	}

	@Override
	public GenericResponse<NotificationResponse> saveNotification(Notification notification) {
		
		GenericResponse<NotificationResponse> response = new GenericResponse<>();
		 NotificationResponse notificationResponse = new NotificationResponse();
		 String operation = request.getRequestURI();
		 try {
			 Notification result = notificationRepository.save(notification);
				
			 notificationResponse.setId(result.getId());
		        notificationResponse.setType(result.getType());
		        notificationResponse.setMessage(result.getMessage());
		        notificationResponse.setEmployeeId(result.getEmployeeId());
		        notificationResponse.setHrId(result.getHrId());
		        notificationResponse.setSendToEmployee(result.getSendToEmployee());
		        notificationResponse.setSendToHR(result.getSendToHR());
		        notificationResponse.setSendToAdmin(result.getSendToAdmin());
		        notificationResponse.setIsRead(result.getIsRead());
		        notificationResponse.setCreatedAt(result.getCreatedAt());
			 
			 
		        if (Boolean.TRUE.equals(result.getSendToEmployee()) && result.getEmployeeId() != null) {
		            notificationWebSocketSender.sendToEmployee(result.getEmployeeId(), notificationResponse);
		        }

		        if (Boolean.TRUE.equals(result.getSendToHR()) && result.getHrId() != null) {
		            notificationWebSocketSender.sendToHR(result.getHrId(), notificationResponse);
		        }

		        if (Boolean.TRUE.equals(result.getSendToAdmin())) {
		            notificationWebSocketSender.sendToAdminDashboard(notificationResponse);
		        }
			 String msg = resposeCode.getMessageByCode(ResponseCode.NOTIFICATION_SENT_SUCCESS, operation);
			System.out.println("Notification Sent Success");
			 response.setIsSuccess(true);
			 response.setMessage(msg);
			 response.setStatus("Success");
			 response.setData(notificationResponse); 
		 } catch (Exception e) {
			 e.printStackTrace();
			 String msg = resposeCode.getMessageByCode(ResponseCode.NOTIFICATION_SENT_FAILED, operation);
			 System.out.println("Notification Sent Failed");
			 response.setIsSuccess(false);
			 response.setMessage(msg);
			 response.setStatus("Failed");
			 response.setData(null); 
		}
		return response;
	}
	
//	@Override
//	public GenericResponse<List<NotificationResponse>> getUserNotifications(Long userId, String operation) {
//
//	    GenericResponse<List<NotificationResponse>> response = new GenericResponse<>();
//
//	    try {
//	        List<Notification> list = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
//
//	        List<NotificationResponse> responseList = new ArrayList<NotificationResponse>();
//
//	        for (Notification notification : list) {
//	            NotificationResponse notificationResponse = new NotificationResponse();
//	            notificationResponse.setId(notification.getId());
//	            notificationResponse.setType(notification.getType());
//	            notificationResponse.setMessage(notification.getMessage());
//	            notificationResponse.setIsRead(notification.getIsRead());
//	            notificationResponse.setCreatedAt(notification.getCreatedAt()); // or formatted date if needed
//	            notificationResponse.setUserId(notification.getUserId());
//	            
//	            responseList.add(notificationResponse);
//	        }
//
//	        String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
//	        response.setIsSuccess(true);
//	        response.setStatus("Success");
//	        response.setMessage(msg);
//	        response.setData(responseList);
//
//	    } catch (Exception e) {
//	        String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
//	        response.setIsSuccess(false);
//	        response.setStatus("Failed");
//	        response.setMessage(msg);
//	        response.setData(null);
//	    }
//
//	    return response;
//	}
	
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

//	@Override
//	public GenericResponse<PagedResponse<NotificationResponse>> getNotificationsWithPagination(Long userId, int page,
//			int size, String operation) {
//		 GenericResponse<PagedResponse<NotificationResponse>> response = new GenericResponse<>();
//
//		 try {
//		    Pageable pageable = PageRequest.of(page, size);
//		    Page<Notification> resultPage = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
//
//		    List<NotificationResponse> list = new ArrayList<NotificationResponse>();
//
//		    for (Notification notification : resultPage.getContent()) {
//		        NotificationResponse notificationResponse = new NotificationResponse();
//		        notificationResponse.setId(notification.getId());
//		        notificationResponse.setUserId(notification.getUserId());
//		        notificationResponse.setType(notification.getType());
//		        notificationResponse.setMessage(notification.getMessage());
//		        notificationResponse.setIsRead(notification.getIsRead());
//		        notificationResponse.setCreatedAt(notification.getCreatedAt());
//		        list.add(notificationResponse);
//		    }
//
//		    PagedResponse<NotificationResponse> paged = new PagedResponse<>();
//		    paged.setContent(list);
//		    paged.setPageNumber(resultPage.getNumber());
//		    paged.setPageSize(resultPage.getSize());
//		    paged.setTotalPages(resultPage.getTotalPages());
//		    paged.setTotalElements(resultPage.getTotalElements());
//
//		    String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
//		    response.setIsSuccess(true);
//		    response.setStatus("Success");
//		    response.setMessage(msg);
//		    response.setData(paged);
//		 } catch (Exception e) {
//			 String msg = resposeCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
//			    response.setIsSuccess(false);
//			    response.setStatus("Failed");
//			    response.setMessage(msg);
//			    response.setData(null);
//		}
//
//		    return response;
//	}

	
	@Override
	public GenericResponse<PagedResponse<NotificationResponse>> getNotificationsWithPagination(
	        Long id, String role, int page, int size, String operation) {

	    GenericResponse<PagedResponse<NotificationResponse>> response = new GenericResponse<>();

	    try {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Notification> resultPage;

	        if (role.equals("ROLE_EMPLOYEE")) {
	            resultPage = notificationRepository.findByEmployeeIdOrderByCreatedAtDesc(id, pageable);
	        } 
	        else if (role.equals("ROLE_HR")) {
	            resultPage = notificationRepository.findByHrIdOrderByCreatedAtDesc(id, pageable);
	        } 
	        else if (role.equals("ROLE_ADMIN")) {
	            resultPage = notificationRepository.findBySendToAdminOrderByCreatedAtDesc(true, pageable);
	        } 
	        else {
	            throw new RuntimeException("Invalid role");
	        }

	        List<NotificationResponse> list = new ArrayList<>();
	        for (Notification notification : resultPage.getContent()) {
	            NotificationResponse dto = new NotificationResponse();
	            dto.setId(notification.getId());
	            dto.setMessage(notification.getMessage());
	            dto.setType(notification.getType());
	            dto.setIsRead(notification.getIsRead());
	            dto.setCreatedAt(notification.getCreatedAt());
	            list.add(dto);
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
	
	
	@Override
	public GenericResponse<String> markAllAsReadByRole(Long userId, String role, String operation) {

	    switch (role) {
	        case "ROLE_EMPLOYEE":
	            notificationRepository.markAllAsReadForEmployee(userId);
	            break;
	        case "ROLE_HR":
	            notificationRepository.markAllAsReadForHR(userId);
	            break;
	        case "ROLE_ADMIN":
	            notificationRepository.markAllAsReadForAdmin();
	            break;
	        default:
	            throw new RuntimeException("Invalid role");
	    }

	    GenericResponse<String> response = new GenericResponse<>();
	    response.setIsSuccess(true);
	    response.setMessage("All notifications marked as read");
	    response.setStatus("Success");
	    return response;
	}


	




}

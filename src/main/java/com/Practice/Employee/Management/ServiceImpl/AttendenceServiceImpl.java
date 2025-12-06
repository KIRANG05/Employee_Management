package com.Practice.Employee.Management.ServiceImpl;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.Practice.Employee.Management.Modal.Attendence;
import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.AttendenceRepository;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.AttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.EmpAttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.AttendenceService;
import com.Practice.Employee.Management.Service.NotificationService;
import com.Practice.Employee.Management.utils.NotificationMessage;
import com.Practice.Employee.Management.utils.NotificationType;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttendenceServiceImpl implements AttendenceService {
	
	private ResponseCodeRespository responseCode;
	private UserRepository userRepository;
	private AttendenceRepository attendenceRepository;
	private NotificationService notificationService;
	private EmployeeRepository employeeRepository;
	
	public AttendenceServiceImpl(ResponseCodeRespository responseCode, UserRepository userRepository, AttendenceRepository attendenceRepository,
			NotificationService notificationService, EmployeeRepository employeeRepository) {
		this.responseCode = responseCode;
		this.userRepository = userRepository;
		this.attendenceRepository = attendenceRepository;
		this.notificationService = notificationService;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public GenericResponse<AttendenceResponse> punchIn(String username, String operation) {
		
		GenericResponse<AttendenceResponse> response = new GenericResponse<>();
		AttendenceResponse attendenceResponse = new AttendenceResponse();
		
		Users user = userRepository.findByUsername(username)
		.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		LocalDate today = LocalDate.now();
		
		Optional<Attendence> existing = attendenceRepository
	            .findFirstByUserIdAndDate(user.getId(), today);
		
//		Attendence attendence = list.isEmpty() ? null : list.get(0);
	            
		
		 if (existing.isPresent()) {
			 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage(msg);
		        response.setData(null);
		        return response;
		    }
		
		Attendence attendence = new Attendence();
		attendence.setUser(user);
		attendence.setDate(today);
		attendence.setLoginTime(LocalDateTime.now());
		
		Attendence saved = attendenceRepository.save(attendence);
		
		attendenceResponse.setId(saved.getUser().getId());
		attendenceResponse.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		attendenceResponse.setLoginTime(saved.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		attendenceResponse.setLogoutTime(null);
		attendenceResponse.setStatus("P");
		
		

		
		String notificationMsg = "You" + " " +
		        NotificationMessage.PUNCH_IN_MESSAGE + " " +
		        saved.getLoginTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
		
		notificationService.sendNotification(user, NotificationType.ATTENDENCE, notificationMsg);
		
		
		String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage(msg);
		response.setData(attendenceResponse);
		
		
		return response;
	}

	@Override
	public GenericResponse<AttendenceResponse> punchOut(String username, String operation) {
		
		GenericResponse<AttendenceResponse> response = new GenericResponse<>();
		AttendenceResponse attendenceResponse = new AttendenceResponse();
		
		Users user = userRepository.findByUsername(username)
		.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		LocalDate today = LocalDate.now();
		
		Optional<Attendence> attendenceOpt = attendenceRepository
	            .findFirstByUserIdAndDate(user.getId(), today);
		
//		Attendence attendence = list.isEmpty() ? null : list.get(0);
	            
		
		 if (attendenceOpt.isEmpty()) {
			 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
		        response.setIsSuccess(true);
		        response.setStatus("Success");
		        response.setMessage(msg);
		        response.setData(null);
		        return response;
		    }
		 Attendence attendence = attendenceOpt.get();
		 
		 if (attendence.getLogoutTime() != null) {
			 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
		        response.setIsSuccess(true);
		        response.setStatus("Success");
		        response.setMessage(msg);
		        response.setData(null);
		        return response;
		    }
		 
		  attendence.setLogoutTime(LocalDateTime.now());
		
		
		Attendence saved = attendenceRepository.save(attendence);
		
		attendenceResponse.setId(saved.getId());
		attendenceResponse.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		attendenceResponse.setLoginTime(saved.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		attendenceResponse.setLogoutTime(saved.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		attendenceResponse.setStatus("P");
		
		
		String notificationMsg = "You" + " " +
		        NotificationMessage.PUNCH_OUT_MESSAGE + " " +
		        saved.getLoginTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
		
		notificationService.sendNotification(user, NotificationType.ATTENDENCE, notificationMsg);
		System.out.println("Punch in Send to save");
		
		String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage(msg);
		response.setData(attendenceResponse);
		
		
		return response;
	}

	@Override
	public GenericResponse<List<AttendenceResponse>> myAttendence(String username, int year, int month, String operation) {
		
		
		GenericResponse<List<AttendenceResponse>> response = new GenericResponse<>();
		List<AttendenceResponse> attendenceResponse = new ArrayList<>();
		
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		List<Attendence> records = attendenceRepository
	            .findAllByUserMonth(user.getId(), year, month);
		
		for (Attendence attendence : records) {
			  AttendenceResponse resp = new AttendenceResponse();
			  resp.setId(attendence.getId());
			  resp.setDate(attendence.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			  resp.setLoginTime(attendence.getLoginTime() != null ? attendence.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null);
			 resp.setLogoutTime(attendence.getLogoutTime() != null ? attendence.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null);
			 resp.setStatus(attendence.getLoginTime() != null ? "P" : "A");
			 
			 attendenceResponse.add(resp);
		}
		
		   String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
		    response.setIsSuccess(true);
		    response.setStatus("Success");
		    response.setMessage(msg);
		    response.setData(attendenceResponse);
		
		return response;
	}

	@Override
	public GenericResponse<List<AttendenceResponse>> adminAttendence(Long employeeId, int year, int month,
			String operation) {
		
		GenericResponse<List<AttendenceResponse>> response = new GenericResponse<>();
		List<AttendenceResponse> attendenceResponse = new ArrayList<>();
		
		List<Attendence> records = attendenceRepository
	            .findAllByUserMonth(employeeId, year, month);
		
		for (Attendence a : records) {

	        AttendenceResponse resp = new AttendenceResponse();
	        resp.setId(a.getId());
	        resp.setDate(a.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	        resp.setStatus(a.getLoginTime() != null ? "P" : "A");

	        resp.setLoginTime(a.getLoginTime() != null ?
	                a.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null);

	        resp.setLogoutTime(a.getLogoutTime() != null ?
	                a.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null);

	        attendenceResponse.add(resp);
	    }

	    String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	    response.setIsSuccess(true);
	    response.setStatus("Success");
	    response.setMessage(msg);
	    response.setData(attendenceResponse);

		
		return response;
	}

	@Override
	public GenericResponse<AttendenceResponse> getTodayStatus(String operation, String username) {
		
		
		GenericResponse<AttendenceResponse> response = new GenericResponse<>();
		
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		LocalDate today = LocalDate.now();
	    Optional<Attendence> attendence = attendenceRepository.findFirstByUserIdAndDate(user.getId(), today);
	    
	    if (attendence.isEmpty()) {
	    	String msg = responseCode.getMessageByCode(ResponseCode.NO_ATTENDENCE_TODAY, operation);
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(null);
	        return response;
	    }
	    
	    Attendence a = attendence.get();
	    AttendenceResponse res = new AttendenceResponse();
	    res.setId(a.getUser().getId());
	    res.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	    res.setLoginTime(a.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    res.setLogoutTime(a.getLogoutTime() != null ? a.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null);
	    res.setStatus("P");

	    String msg = responseCode.getMessageByCode(ResponseCode.ATTENDENCE_FOUND, operation);
	    response.setIsSuccess(true);
	    response.setStatus("Success");
	    response.setMessage(msg);
	    response.setData(res);
	    return response;
		
		
	}
	
	
	@Override
	public GenericResponse<AttendenceResponse> getTodayStatusByEmpId(String operation, Long empId) {

	    GenericResponse<AttendenceResponse> response = new GenericResponse<>();

	    Users user = userRepository.findById(empId)
	            .orElseThrow(() -> new RuntimeException("User Not Found"));

	    LocalDate today = LocalDate.now();
	    Optional<Attendence> attendence = attendenceRepository.findFirstByUserIdAndDate(user.getId(), today);

	    // If no attendance entry for today
	    if (attendence.isEmpty()) {
	    	String msg = responseCode.getMessageByCode(ResponseCode.NO_ATTENDENCE_TODAY, operation);
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(null);
	        return response;
	    }

	    Attendence a = attendence.get();
	    AttendenceResponse res = new AttendenceResponse();
	    res.setId(a.getId());
	    res.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	    res.setLoginTime(a.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    res.setLogoutTime(a.getLogoutTime() != null ? a.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : null);
	    res.setStatus("P");

	    String msg = responseCode.getMessageByCode(ResponseCode.ATTENDENCE_FOUND, operation);
	    response.setIsSuccess(true);
	    response.setStatus("Success");
	    response.setMessage(msg);
	    response.setData(res);
	    return response;
	}

	@Override
	public GenericResponse<List<EmpAttendenceResponse>> getAllTodayAttendance(String operation) {
		
		 GenericResponse<List<EmpAttendenceResponse>> response = new GenericResponse<>();
		 List<EmpAttendenceResponse> responseList  = new ArrayList<>();
		 
		 try {
		 LocalDate today = LocalDate.now();
		 List<Employee> employees = employeeRepository.findAll();
		 
		 if (employees == null || employees.isEmpty()) {
	            String msg = responseCode.getMessageByCode(ResponseCode.NO_EMPLOYEES_FOUND, operation);
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage(msg);
	            response.setData(null);
	            return response;
	        }
		 
		 employees.forEach(emp -> {
		        EmpAttendenceResponse empAttendenceResponse = new EmpAttendenceResponse();
		        empAttendenceResponse.setEmpId(emp.getId());

		        Attendence attendence = attendenceRepository.findFirstByUserIdAndDate(emp.getId(), today).orElse(null);

		        if (attendence != null) {
		        	empAttendenceResponse.setId(attendence.getId());
		        	empAttendenceResponse.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		        	empAttendenceResponse.setLoginTime(attendence.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		        	empAttendenceResponse.setLogoutTime(attendence.getLogoutTime() != null
		                    ? attendence.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
		                    : null);
		        	empAttendenceResponse.setStatus("P");
		        } else {
		        	empAttendenceResponse.setStatus("A");
		        }

		        responseList.add(empAttendenceResponse);
		    });
		 
		 String msg = responseCode.getMessageByCode(ResponseCode.ATTENDENCE_FOUND, operation);
		    response.setIsSuccess(true);
		    response.setStatus("Success");
		    response.setMessage(msg);
		    response.setData(responseList);
	} catch (Exception ex) {
        // ERROR RESPONSE
        response.setIsSuccess(false);
        response.setStatus("Error");
        response.setMessage("Something went wrong while fetching attendance");
        response.setData(null);
    }
		 return response;
	}
	
}

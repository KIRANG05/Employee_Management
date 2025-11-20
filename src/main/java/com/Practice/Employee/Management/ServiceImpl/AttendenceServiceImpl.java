package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Attendence;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.AttendenceRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.AttendenceResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.AttendenceService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class AttendenceServiceImpl implements AttendenceService {
	
	private ResponseCodeRespository responseCode;
	private UserRepository userRepository;
	private AttendenceRepository attendenceRepository;
	
	public AttendenceServiceImpl(ResponseCodeRespository responseCode, UserRepository userRepository, AttendenceRepository attendenceRepository) {
		this.responseCode = responseCode;
		this.userRepository = userRepository;
		this.attendenceRepository = attendenceRepository;
	}

	@Override
	public GenericResponse<AttendenceResponse> punchIn(String username, String operation) {
		
		GenericResponse<AttendenceResponse> response = new GenericResponse<>();
		AttendenceResponse attendenceResponse = new AttendenceResponse();
		
		Users user = userRepository.findByUsername(username)
		.orElseThrow(() -> new RuntimeException("User Not Found"));
		
		LocalDate today = LocalDate.now();
		
		Optional<Attendence> existing = attendenceRepository
	            .findByEmployeeUsernameAndDate(username, today);
		
		 if (existing.isPresent()) {
			 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage(msg);
		        response.setData(null);
		        return response;
		    }
		
		Attendence attendence = new Attendence();
		attendence.setEmployee(user);
		attendence.setDate(today);
		attendence.setLoginTime(LocalDateTime.now());
		
		Attendence saved = attendenceRepository.save(attendence);
		
		attendenceResponse.setId(saved.getEmployee().getId());
		attendenceResponse.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		attendenceResponse.setLoginTime(saved.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		attendenceResponse.setLogoutTime(null);
		attendenceResponse.setStatus("P");
		
		
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
		
		Attendence attendence = attendenceRepository
	            .findByEmployeeUsernameAndDate(username, today)
	            .orElse(null);
		
		 if (attendence == null ) {
			 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage(msg);
		        response.setData(null);
		        return response;
		    }
		 
		 if (attendence.getLogoutTime() != null) {
		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage("Already punched out today");
		        response.setData(null);
		        return response;
		    }
		 
		  attendence.setLogoutTime(LocalDateTime.now());
		
		
		Attendence saved = attendenceRepository.save(attendence);
		
		attendenceResponse.setId(saved.getEmployee().getId());
		attendenceResponse.setDate(today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		attendenceResponse.setLoginTime(saved.getLoginTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		attendenceResponse.setLogoutTime(saved.getLogoutTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		attendenceResponse.setStatus("P");
		
		
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
	            .findAllByEmployeeMonth(user.getId(), year, month);
		
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
	            .findAllByEmployeeMonth(employeeId, year, month);
		
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

}

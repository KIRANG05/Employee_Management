package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.Modal.LeaveStatus;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.LeaveRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.LeaveResponse;
import com.Practice.Employee.Management.Service.LeaveService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class LeaveServiceImpl implements LeaveService {
	
	
	private LeaveRepository leaveRepository;
	
	private ResponseCodeRespository responseCodeRespository;
	
	private UserRepository userRepository;
	
	public LeaveServiceImpl(LeaveRepository leaveRepository,UserRepository userRepository, ResponseCodeRespository responseCodeRepository ) {
		
		this.leaveRepository = leaveRepository;
		this.userRepository = userRepository;
		this.responseCodeRespository = responseCodeRepository;
	}
	

	@Override
	public GenericResponse<LeaveResponse> applyLeave(Long employeeId, LeaveRequest leaveRequest, String operation) {
		
		GenericResponse<LeaveResponse> response = new GenericResponse<>();
		
		Optional<Users> result = userRepository.findById(employeeId);
				
		if (result.isPresent()) {
			
			 String validationError = validateLeaveDates(leaveRequest);
			    if (validationError != null) {
			        return buildError(validationError);
			    }
			
			Users user = result.get();
			Role role = user.getRole();
			System.out.println("role is" + role);
			
			
			leaveRequest.setEmployee(user);
//		    leaveRequest.setStatus(LeaveStatus.PENDING);
			if (role == Role.ROLE_HR) {
				leaveRequest.setStatus(LeaveStatus.APPROVED);
			} else {
				leaveRequest.setStatus(LeaveStatus.PENDING);
			}
		    leaveRequest.setAppliedAt(LocalDateTime.now());
		    
		    LeaveRequest savedLeave = leaveRepository.save(leaveRequest);
		    
		    LeaveResponse leaveResponse = new LeaveResponse();
			
		    String msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_APPLIED_SUCCESS, operation);
		    leaveResponse.setId(savedLeave.getId());
		    leaveResponse.setEmployeeName(savedLeave.getEmployee().getUsername());
		    leaveResponse.setFromDate(savedLeave.getFromDate());
		    leaveResponse.setToDate(savedLeave.getToDate());
		    leaveResponse.setAppliedAt(savedLeave.getAppliedAt());
		    leaveResponse.setLeaveStatus(savedLeave.getStatus());
		    leaveResponse.setLeaveType(savedLeave.getLeaveType());
		    leaveResponse.setReason(savedLeave.getReason());
		    
		    response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(leaveResponse);
			
			
		} else {
			 String msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_APPLIED_FAILED, operation);
			 response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage(msg);
		        response.setData(null);
		}
		
		
		return response;
	}
	
	
	private String validateLeaveDates(LeaveRequest request) {

	    LocalDate today = LocalDate.now();

	    if (request.getFromDate().isBefore(today)) {
	        return "From Date cannot be in the past";
	    }

	    if (request.getToDate().isBefore(today)) {
	        return "To Date cannot be in the past";
	    }

	    if (request.getToDate().isBefore(request.getFromDate())) {
	        return "To Date cannot be earlier than From Date";
	    }

	    return null; // no errors
	}

	
	private GenericResponse<LeaveResponse> buildError(String message) {
	    GenericResponse<LeaveResponse> response = new GenericResponse<>();
	    response.setIsSuccess(false);
	    response.setStatus("Failed");
	    response.setMessage(message);
	    response.setData(null);
	    return response;
	}



	@Override
	public GenericResponse<List<LeaveResponse>> getAllLeaves(String operation) {
		
		GenericResponse<List<LeaveResponse>> response = new GenericResponse<>();
		
		List<LeaveRequest> leaveRequests = leaveRepository.findAll();
		
		if (!leaveRequests.isEmpty()) {
			List<LeaveResponse> leaves = leaveRequests.stream().map(leave -> {
				LeaveResponse leaveResponse = new LeaveResponse();
				
				leaveResponse.setId(leave.getId());
				leaveResponse.setEmployeeName(leave.getEmployee().getUsername());
				leaveResponse.setFromDate(leave.getFromDate());
				leaveResponse.setToDate(leave.getToDate());
				leaveResponse.setReason(leave.getReason());
				leaveResponse.setLeaveStatus(leave.getStatus());
				leaveResponse.setAppliedAt(leave.getAppliedAt());
				leaveResponse.setLeaveType(leave.getLeaveType());
		        return leaveResponse;
			}).toList();
			
			
			String msg  = responseCodeRespository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setData(leaves);
		} else {
			String msg  = responseCodeRespository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
		
		}
		return response;
	}


	@Override
	public GenericResponse<LeaveResponse> updateLeaveStatus(Long leaveId, LeaveStatus newStatus, String operation) {
		
		 GenericResponse<LeaveResponse> response = new GenericResponse<>();

	        LeaveRequest leave = leaveRepository.findById(leaveId)
	                .orElse(null);
	        
	        if (leave == null) {
	        	String msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_NOT_FOUND, operation);
	            response.setIsSuccess(false);
	            response.setStatus("Failed");
	            response.setMessage(msg);
	            return response;
	        }

	        leave.setStatus(newStatus);
	        LeaveRequest saved = leaveRepository.save(leave);

	        // Map only the updated leave to response
	        LeaveResponse leaveResponse = new LeaveResponse();
	        leaveResponse.setId(saved.getId());
	        leaveResponse.setLeaveStatus(saved.getStatus());
	        leaveResponse.setLeaveType(saved.getLeaveType());
	        leaveResponse.setEmployeeName(saved.getEmployee().getUsername());
	        leaveResponse.setReason(saved.getReason());
	        leaveResponse.setFromDate(saved.getFromDate());
	        leaveResponse.setToDate(saved.getToDate());
	        String msg = null;
	        
	        if (saved.getStatus() == LeaveStatus.APPROVED) {
	        	 msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_APPROVED_SUCCESS, operation);
	        } else if (saved.getStatus() == LeaveStatus.REJECTED) {
	        	 msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_REJECT_SUCCESS, operation);
	        }

	        
	        response.setIsSuccess(true);
	        response.setMessage(msg);
	        response.setData(leaveResponse);
		
		return response;
	}

	
	@Override
	public GenericResponse<List<LeaveResponse>> getLeaveSummary(Long employeeId, String operation) {

	    GenericResponse<List<LeaveResponse>> response = new GenericResponse<>();

	    List<LeaveRequest> leaves = leaveRepository.findByEmployeeId(employeeId);

	    if (leaves == null || leaves.isEmpty()) {
	        String msg = responseCodeRespository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(msg);
	        return response;
	    }

	    List<LeaveResponse> leaveResponses = new ArrayList<>();

	    for (LeaveRequest leave : leaves) {
	        LeaveResponse lr = new LeaveResponse();
	        lr.setId(leave.getId());
	        lr.setEmployeeName(leave.getEmployee().getUsername());
	        lr.setLeaveType(leave.getLeaveType());
	        lr.setLeaveStatus(leave.getStatus());
	        lr.setReason(leave.getReason());
	        lr.setFromDate(leave.getFromDate());
	        lr.setToDate(leave.getToDate());
	        lr.setAppliedAt(leave.getAppliedAt());
	        leaveResponses.add(lr);
	    }

	    String msg = responseCodeRespository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	    response.setIsSuccess(true);
	    response.setStatus("Success");
	    response.setMessage(msg);
	    response.setData(leaveResponses);

	    return response;
	}

}

package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.Modal.LeaveStatus;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.LeaveRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
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
	public LeaveResponse applyLeave(Long employeeId, LeaveRequest leaveRequest, String operation) {
		
		LeaveResponse response = new LeaveResponse();
		
		Optional<Users> result = userRepository.findById(employeeId);
				
		if (result.isPresent()) {
			Users user = result.get();
			
			leaveRequest.setEmployee(user);
		    leaveRequest.setStatus(LeaveStatus.PENDING);
		    leaveRequest.setAppliedAt(LocalDateTime.now());
		    
		    LeaveRequest savedLeave = leaveRepository.save(leaveRequest);
			
		    String msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_APPLIED_SUCCESS, operation);
			response.setId(savedLeave.getId());
			response.setEmployeeName(savedLeave.getEmployee().getUsername());
			response.setFromDate(savedLeave.getFromDate());
			response.setToDate(savedLeave.getToDate());
			response.setAppliedAt(savedLeave.getAppliedAt());
			response.setleaveStatuss(savedLeave.getStatus());
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			
		} else {
			 String msg = responseCodeRespository.getMessageByCode(ResponseCode.LEAVE_APPLIED_FAILED, operation);
				response.setIsSuccess(false);
				response.setMessage(msg);
				response.setStatus("Failed");
		}
		
		
		return response;
	}

}

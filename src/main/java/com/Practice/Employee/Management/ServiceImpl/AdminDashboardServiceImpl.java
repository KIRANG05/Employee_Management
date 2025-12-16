package com.Practice.Employee.Management.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.LeaveStatus;
import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.TaskStatus;
import com.Practice.Employee.Management.Repository.AttendenceRepository;
import com.Practice.Employee.Management.Repository.LeaveRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.TaskRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.AdminDashboardSummaryResponse;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.Service.AdminDashboardService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService{
	
	private final UserRepository userRepository;
    private final AttendenceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;
    private final TaskRespository taskRepository;
    private final ResponseCodeRespository responseCode;
    
    public AdminDashboardServiceImpl(UserRepository userRepository, AttendenceRepository attendanceRepository,
    		LeaveRepository leaveRepository, TaskRespository taskRepository, ResponseCodeRespository responseCode ) {
    	this.userRepository = userRepository;
    	this.attendanceRepository = attendanceRepository;
    	this.leaveRepository = leaveRepository;
    	this.taskRepository = taskRepository;
    	this.responseCode = responseCode;
    	
    	
    }
    

	@Override
	public GenericResponse<AdminDashboardSummaryResponse> getDashboardSummary(String operation) {
		
		GenericResponse<AdminDashboardSummaryResponse> response = new GenericResponse<>();
		try {
        long totalEmployees = userRepository.count();
        long totalHRs = userRepository.countByRole(Role.ROLE_HR);
        long presentToday = attendanceRepository.countTodayAttendance();
        long onLeaveToday = leaveRepository.countTodayOnLeave();
        long activeTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long pendingLeaves = leaveRepository.countByStatus(LeaveStatus.PENDING);
        long approvedLeaves = leaveRepository.countByStatus(LeaveStatus.APPROVED);
        
        Map<String, Long> roleCounts = new HashMap<>();

        List<Object[]> roleData = userRepository.countUsersByRole();
        for (Object[] row : roleData) {
            String role = row[0].toString();
            Long count = (Long) row[1];
            roleCounts.put(role, count);
        }

        
        AdminDashboardSummaryResponse adminDashboardSummaryResponse = new AdminDashboardSummaryResponse();
        adminDashboardSummaryResponse.setTotalEmployees(totalEmployees);
        adminDashboardSummaryResponse.setTotalHRs(totalHRs);
        adminDashboardSummaryResponse.setPresentToday(presentToday);
        adminDashboardSummaryResponse.setOnLeaveToday(onLeaveToday);
        adminDashboardSummaryResponse.setActiveTasks(activeTasks);
        adminDashboardSummaryResponse.setPendingLeaves(pendingLeaves);
        adminDashboardSummaryResponse.setApprovedLeaves(approvedLeaves);
        adminDashboardSummaryResponse.setRoleCounts(roleCounts);
        
        String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
        response.setIsSuccess(true);
        response.setMessage(msg);
        response.setStatus("Success");
        response.setData(adminDashboardSummaryResponse);
		} catch (Exception e) {
			 String msg = responseCode.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
		        response.setIsSuccess(false);
		        response.setMessage(msg);
		        response.setStatus("Failed");
		}
		
		return response;
	}

}

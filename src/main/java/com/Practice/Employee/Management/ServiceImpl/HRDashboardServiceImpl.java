package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.LeaveStatus;
import com.Practice.Employee.Management.Modal.TaskStatus;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.LeaveRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.TaskRespository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.HRDashboardSummaryResponse;
import com.Practice.Employee.Management.ResponseModal.ReportResponse;
import com.Practice.Employee.Management.Service.HRDashboardService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class HRDashboardServiceImpl implements HRDashboardService{
	
	
	private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;
    private final TaskRespository taskRepository;
    private final ResponseCodeRespository responseCodeRepository;
    
    public HRDashboardServiceImpl(EmployeeRepository employeeRepository, LeaveRepository leaveRepository,
    		TaskRespository taskRepository, ResponseCodeRespository responseCodeRepository) {
    	
    	this.employeeRepository = employeeRepository;
    	this.leaveRepository = leaveRepository;
    	this.taskRepository = taskRepository;
    	this.responseCodeRepository = responseCodeRepository;
    	
    }

	@Override
	public GenericResponse<HRDashboardSummaryResponse> getHRDashboardSummary(String operation) {
		
		GenericResponse<HRDashboardSummaryResponse> response = new GenericResponse<>();
		try {
        Long totalReportees = employeeRepository.count();
        Long pendingLeaves = leaveRepository.countByStatus(LeaveStatus.PENDING);
        Long tasksAssignedToday = taskRepository.countByAssignedDate(LocalDate.now());
        
        Long totalTasks = taskRepository.count();
        Long completedTasks = taskRepository.countByStatus(TaskStatus.COMPLETED);
        Long pendingTasks = taskRepository.countByStatus(TaskStatus.PENDING);
        Long overDueTasks = taskRepository.countOverDueTasks(LocalDate.now(), TaskStatus.COMPLETED);
        
        ReportResponse taskSummary = new ReportResponse();
        taskSummary.setTotalTasks(totalTasks);
        taskSummary.setCompletedTasks(completedTasks);
        taskSummary.setPendingTasks(pendingTasks);
        taskSummary.setOverDueTasks(overDueTasks);
        
        HRDashboardSummaryResponse hrDashboardResponse = new HRDashboardSummaryResponse();
        
        hrDashboardResponse.setTotalReportees(totalReportees);
        hrDashboardResponse.setPendingLeaveApprovals(pendingLeaves);
        hrDashboardResponse.setTasksAssignedToday(tasksAssignedToday);
        hrDashboardResponse.setTaskSummary(taskSummary);
        
        

        String msg = responseCodeRepository
                .getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);

        response.setIsSuccess(true);
        response.setStatus("Success");
        response.setMessage(msg);
        response.setData(hrDashboardResponse);

		} catch (Exception e) {
			e.printStackTrace(); 
			 String msg = responseCodeRepository
		                .getMessageByCode(ResponseCode.GENERIC_FAIL, operation);

		        response.setIsSuccess(false);
		        response.setStatus("Failed");
		        response.setMessage(msg);
		       
		} 
		return response;
	}

}

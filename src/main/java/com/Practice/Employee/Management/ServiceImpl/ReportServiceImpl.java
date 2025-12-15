package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.TaskStatus;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.TaskRespository;
import com.Practice.Employee.Management.ResponseModal.ReportResponse;
import com.Practice.Employee.Management.Service.ReportService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class ReportServiceImpl implements ReportService {
	

	private final TaskRespository taskRepository;
    private final ResponseCodeRespository responseCodeRepository;

    public ReportServiceImpl(TaskRespository taskRepository, ResponseCodeRespository responseCodeRepository) {
        this.taskRepository = taskRepository;
        this.responseCodeRepository = responseCodeRepository;
    }
	
	@Override
	public ReportResponse getTaskSummary(String operation) {
		ReportResponse response = new ReportResponse();
		
		try {
			Long totalTasks = taskRepository.count();
			Long completedTasks = taskRepository.countByStatus(TaskStatus.COMPLETED);
			Long pendingTasks = taskRepository.countByStatus(TaskStatus.PENDING);
			Long overdueTasks = taskRepository.countOverdue(LocalDate.now(), TaskStatus.COMPLETED);
			
			
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			
			response.setCompletedTasks(completedTasks);
			response.setPendingTasks(pendingTasks);
			response.setTotalTasks(totalTasks);
			response.setOverDueTasks(overdueTasks);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
		} catch (Exception e) {
			e.printStackTrace();
			response.setIsSuccess(false);
			response.setMessage(responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation));
			response.setStatus("Failed");
		}
		
		
		return response;
	}

	@Override
	public ReportResponse getTaskSummaryByEmployee(String employeeName, String operation) {
		ReportResponse response = new ReportResponse();
		Long totalTasks = taskRepository.countByAssignedTo(employeeName);
		Long completedtasks = taskRepository.countByAssignedToAndStatus(employeeName, TaskStatus.COMPLETED);
		Long pendingTasks = taskRepository.countByAssignedToAndStatus(employeeName, TaskStatus.PENDING);
		Long overDueTasks = taskRepository.countOverdueByEmployee(employeeName,LocalDate.now(), TaskStatus.COMPLETED);
		
		try {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setTotalTasks(totalTasks);
			response.setCompletedTasks(completedtasks);
			response.setPendingTasks(pendingTasks);
			response.setOverDueTasks(overDueTasks);
		} catch (Exception e) {
			response.setIsSuccess(false);
			response.setMessage(responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation));
			response.setStatus("Failed");
		}
		
		return response;
	}

	
	@Override
	public ReportResponse getTaskSummaryByHr(String hrName, String operation) {
	    ReportResponse response = new ReportResponse();
	    
	    try {
	        Long totalTasks = taskRepository.countByAssignedBy(hrName);
	        Long completedTasks = taskRepository.countByAssignedByAndStatus(hrName, TaskStatus.COMPLETED);
	        Long pendingTasks = taskRepository.countByAssignedByAndStatus(hrName, TaskStatus.PENDING);
	        Long overDueTasks = taskRepository.countOverdueByAssignedBy(hrName, LocalDate.now(), TaskStatus.COMPLETED);
	        System.out.println("HR Name: " + hrName + ", Today: " + LocalDate.now());
	        Long tasksAssignedToday = taskRepository.countByAssignedByAndAssignedDate(hrName, LocalDate.now());
	        System.out.println("Tasks assigned today: " + tasksAssignedToday);
	        
//	        LocalDate today = LocalDate.now();
//	        LocalDateTime startOfDay = today.atStartOfDay();
//	        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
//	        Long tasksAssignedToday = taskRepository.countByAssignedByAndAssignedDate(hrName, startOfDay, endOfDay);


	        String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);

	        response.setIsSuccess(true);
	        response.setMessage(msg);
	        response.setStatus("Success");
	        response.setTotalTasks(totalTasks);
	        response.setCompletedTasks(completedTasks);
	        response.setPendingTasks(pendingTasks);
	        response.setOverDueTasks(overDueTasks);
	        response.setTaskAssignedToday(tasksAssignedToday);

	    } catch (Exception e) {
	        response.setIsSuccess(false);
	        response.setMessage(responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation));
	        response.setStatus("Failed");
	    }

	    return response;
	}

}

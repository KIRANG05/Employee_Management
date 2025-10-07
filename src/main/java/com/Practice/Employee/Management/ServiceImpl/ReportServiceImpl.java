package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;

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

}

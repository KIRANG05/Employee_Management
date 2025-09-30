package com.Practice.Employee.Management.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Task;
import com.Practice.Employee.Management.Modal.TaskStatus;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.TaskRespository;
import com.Practice.Employee.Management.ResponseModal.TaskResponse;
import com.Practice.Employee.Management.Service.TaskService;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.persistence.criteria.Predicate;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	ResponseCodeRespository responseCodeRepository;


	private TaskRespository taskRepository;


	public TaskServiceImpl(TaskRespository taskrepository) {
		this.taskRepository = taskrepository;
	}

	@Override
	public TaskResponse addTask(Task task, String opeartion) {

		TaskResponse response = new TaskResponse();

		if (task.getStatus() == null) {
			task.setStatus(TaskStatus.PENDING);
		}
		if (task.getAssignedDate() == null) {
			task.setAssignedDate(LocalDate.now());
		}
		Task result = taskRepository.save(task);

		if(result != null) {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_ADDED_SUCCESS, opeartion);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setTask(result);
		} else {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_ADDED_FAILED, opeartion);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
		}

		return response;
	}

	@Override
	public TaskResponse getAllTasks(String operation) {

		TaskResponse response = new TaskResponse();

		List<Task> result = taskRepository.findAll();

		if(result != null) {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setTasks(result);
		}else {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
		}

		return response;
	}

	@Override
	public TaskResponse filterTasks(String fromDateStr, String toDateStr, String assignedBy, String assignedTo,
			String operation) {
		TaskResponse response = new TaskResponse();

		LocalDate fromDate = fromDateStr != null ? LocalDate.parse(fromDateStr) : null;
		LocalDate toDate = toDateStr != null ? LocalDate.parse(toDateStr) : null;


		Specification<Task> spec = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (fromDate != null && toDate != null) {
				// Both fromDate and toDate → filter range
				predicates.add(cb.between(root.get("dueDate"), fromDate, toDate));
			} else if (fromDate != null) {
				// Only fromDate → filter exactly that date
				predicates.add(cb.equal(root.get("dueDate"), fromDate));
			}

			if (assignedBy != null) {
				predicates.add(cb.equal(root.get("assignedBy"), assignedBy));
			}

			if (assignedTo != null) {
				predicates.add(cb.equal(root.get("assignedTo"), assignedTo));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
		List<Task> result = taskRepository.findAll(spec);

		if(result != null) {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setTasks(result);
		}else {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
		}

		return response;
	}





}

package com.Practice.Employee.Management.ServiceImpl;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Notification;
import com.Practice.Employee.Management.Modal.Task;
import com.Practice.Employee.Management.Modal.TaskStatus;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.EmployeeRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.TaskRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.TaskResponse;
import com.Practice.Employee.Management.Service.NotificationService;
import com.Practice.Employee.Management.Service.TaskService;
import com.Practice.Employee.Management.utils.NotificationMessage;
import com.Practice.Employee.Management.utils.NotificationType;
import com.Practice.Employee.Management.utils.ResponseCode;

import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	ResponseCodeRespository responseCodeRepository;


	private TaskRespository taskRepository;
	private EmployeeRepository employeeRepository;
	private NotificationService notificationService;
	private UserRepository userRepository;


	public TaskServiceImpl(TaskRespository taskrepository, EmployeeRepository employeeRepository, NotificationService notificationService,
			UserRepository userRepository) {
		this.taskRepository = taskrepository;
		this.employeeRepository = employeeRepository;
		this.notificationService = notificationService;
		this.userRepository = userRepository;
	}

	@Override
	public GenericResponse<TaskResponse> addTask(Task task, String opeartion) {
		
		GenericResponse<TaskResponse> response = new GenericResponse<>();
		TaskResponse taskResponse = new TaskResponse();
		
		 Users assignedToUser = userRepository
		            .findByUsername(task.getAssignedTo())
		            .orElseThrow(() -> new RuntimeException("Assigned user not found"));

		    Users assignedByUser = userRepository
		            .findByUsername(task.getAssignedBy())
		            .orElseThrow(() -> new RuntimeException("Assigned-by user not found"));

		    task.setAssignedToUserId(assignedToUser.getId());
		    task.setAssignedByUserId(assignedByUser.getId());

		if (task.getStatus() == null) {
			task.setStatus(TaskStatus.PENDING);
		}
		if (task.getAssignedDate() == null) {
			task.setAssignedDate(LocalDate.now());
		}
		Task result = taskRepository.save(task);

		if(result != null) {
			taskResponse.setTask(result);
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_ADDED_SUCCESS, opeartion);
			response.setIsSuccess(true);
			response.setMessage(msg);
			response.setStatus("Success");
			response.setData(taskResponse);
			
			Notification empNotification = new Notification();
			empNotification.setType(NotificationType.TASK_ASSIGNED);
			empNotification.setMessage("New task assigned: " + result.getTaskName());
			empNotification.setEmployeeId(result.getAssignedToUserId());
			empNotification.setSendToEmployee(true);       // push to employee dashboard
			empNotification.setSendToHR(false);
			empNotification.setSendToAdmin(false);
			
			notificationService.saveNotification(empNotification);
			System.out.println("Notification Called");
			
			Notification adminNotification = new Notification();
			adminNotification.setType(NotificationType.TASK_ASSIGNED);
			adminNotification.setMessage(result.getAssignedBy() + " assigned a task to " + result.getAssignedTo());
			
			adminNotification.setSendToEmployee(false);       // push to employee dashboard
			adminNotification.setSendToHR(false);
			adminNotification.setSendToAdmin(true);
			
			notificationService.saveNotification(adminNotification);
			System.out.println("Notification Called");
			
			
			
		} else {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_ADDED_FAILED, opeartion);
			response.setIsSuccess(false);
			response.setMessage(msg);
			response.setStatus("Failed");
		}

		return response;
	}

	@Override
	public GenericResponse<TaskResponse> getAllTasks(String operation) {
		GenericResponse<TaskResponse> response = new GenericResponse<>();

		TaskResponse taskResponse = new TaskResponse();

		List<Task> result = taskRepository.findAll();

		if(result != null) {
			taskResponse.setTasks(result);
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setData(taskResponse);
		}else {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
		}

		return response;
	}

	@Override
	public GenericResponse<TaskResponse> filterTasks(String fromDateStr, String toDateStr, String assignedBy, String assignedTo,
			TaskStatus status, String operation) {
		GenericResponse<TaskResponse> response = new GenericResponse<>();
		
		TaskResponse taskResponse = new TaskResponse();

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

			if (status != null) {
			    predicates.add(cb.equal(root.get("status"), status));
			}




			return cb.and(predicates.toArray(new Predicate[0]));
		};
		List<Task> result = taskRepository.findAll(spec);

		if(result != null) {
			taskResponse.setTasks(result);
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setData(taskResponse);
		}else {
			String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
			response.setIsSuccess(false);
			response.setStatus("Failed");
			response.setMessage(msg);
		}

		return response;
	}

	@Override
	public GenericResponse<TaskResponse> getTaskById(Long id, String operation) {
		GenericResponse<TaskResponse> response = new GenericResponse<>();
	    TaskResponse taskResponse = new TaskResponse();

	    Optional<Task> taskOpt = taskRepository.findById(id);
	    if (taskOpt.isPresent()) {
	    	taskResponse.setTask(taskOpt.get());
	        String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_SUCCESS, operation);
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(taskResponse);
	        
	    } else {
	        String msg = responseCodeRepository.getMessageByCode(ResponseCode.GENERIC_FAIL, operation);
	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(msg);
	    }

	    return response;
	}

//	@Override
//	public GenericResponse<TaskResponse> updateTask(Long id, Task task, String operation) {
//		GenericResponse<TaskResponse> response =  new GenericResponse<>();
//		
//		TaskResponse taskResponse = new TaskResponse();
//		
//		
//		
//		Task existingTask = taskRepository.findById(id).orElse(null);
//		
//		if(existingTask != null) {
//			
//			existingTask.setTaskName(task.getTaskName());
//			existingTask.setDescription(task.getDescription());
//			existingTask.setAssignedBy(task.getAssignedBy());
//			existingTask.setStatus(task.getStatus());
//			existingTask.setPriority(task.getPriority());
//			existingTask.setAssignedDate(task.getAssignedDate());
//			existingTask.setDueDate(task.getDueDate());
//
//		        // update assignedTo employee
//			if (task.getAssignedTo() != null && !task.getAssignedTo().isEmpty()) {
//			    existingTask.setAssignedTo(task.getAssignedTo()); // just set the name
//			}
//
//
//		        Task saved = taskRepository.save(existingTask);
//		        
//		        taskResponse.setTask(saved);
//
//		        String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_UPDATE_SUCCESS, operation);
//		        response.setIsSuccess(true);
//		        response.setMessage(msg);
//		        response.setData(taskResponse);
//		        response.setStatus("Success");
//		        
//		} else {
//			 String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_UPDATE_FAILED, operation);
//			 response.setIsSuccess(false);
//			 response.setMessage(msg);
//			 response.setStatus("Failed");
//		}
//		
//		return response;
//	}
	
	@Override
	@Transactional
	public GenericResponse<TaskResponse> updateTask(
	        Long taskId,
	        Task updatedTask,
	        Principal principal,
	        String operation) {

	    GenericResponse<TaskResponse> response = new GenericResponse<>();
	    TaskResponse taskResponse = new TaskResponse();

	    String username = principal.getName();

	    Users loggedInUser = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    String role = loggedInUser.getRole().name(); // ADMIN / HR / EMPLOYEE

	    Task existingTask = taskRepository.findById(taskId).orElse(null);

	    if (existingTask == null) {
	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage(
	                responseCodeRepository.getMessageByCode(
	                        ResponseCode.TASK_UPDATE_FAILED, operation));
	        return response;
	    }


	    //  HR / ADMIN cannot modify completed task
	    if ((role.equals("ROLE_HR") || role.equals("ROLE_EMPLOYEE"))
	            && existingTask.getStatus() == TaskStatus.COMPLETED) {

	        response.setIsSuccess(false);
	        response.setStatus("Failed");
	        response.setMessage("Completed task cannot be modified");
	        return response;
	    }

	    if (role.equals("ROLE_EMPLOYEE")) {

	        // Employee can update ONLY status
	        existingTask.setStatus(updatedTask.getStatus());

	        Task saved = taskRepository.save(existingTask);
	        taskResponse.setTask(saved);

	        // 🔔 Notify HR + Admin
	        Notification notification = new Notification();
	        notification.setType(NotificationType.TASK_STATUS_UPDATED);
	        notification.setMessage(
	                loggedInUser.getUsername() +
	                " updated task status to " +
	                updatedTask.getStatus());

	        notification.setHrId(existingTask.getAssignedByUserId());
	        notification.setSendToEmployee(false);
	        notification.setSendToHR(true);
	        notification.setSendToAdmin(true);

	        notificationService.saveNotification(notification);

	        String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_STATUS_UPDATE_SUCCESS, operation);
	        response.setIsSuccess(true);
	        response.setStatus("Success");
	        response.setMessage(msg);
	        response.setData(taskResponse);

	        return response;
	    }

	    /* ===============================
	       5️⃣ HR / ADMIN FULL UPDATE
	       =============================== */

	    existingTask.setTaskName(updatedTask.getTaskName());
	    existingTask.setDescription(updatedTask.getDescription());
	    existingTask.setPriority(updatedTask.getPriority());
	    existingTask.setDueDate(updatedTask.getDueDate());
	    existingTask.setStatus(updatedTask.getStatus());

	    /* ===============================
	       6️⃣ HANDLE RE-ASSIGN
	       =============================== */
	    boolean reassigned = false;

	    if (updatedTask.getAssignedToUserId() != null &&
	        !updatedTask.getAssignedToUserId()
	                .equals(existingTask.getAssignedToUserId())) {

	        existingTask.setAssignedToUserId(updatedTask.getAssignedToUserId());
	        existingTask.setAssignedTo(updatedTask.getAssignedTo());
	        reassigned = true;
	    }
	    
	    if (updatedTask.getTaskName() != null) {
	        existingTask.setTaskName(updatedTask.getTaskName());
	    }

	    if (updatedTask.getDescription() != null) {
	        existingTask.setDescription(updatedTask.getDescription());
	    }

	    if (updatedTask.getPriority() != null) {
	        existingTask.setPriority(updatedTask.getPriority());
	    }

	    if (updatedTask.getDueDate() != null) {
	        existingTask.setDueDate(updatedTask.getDueDate());
	    }

	    if (updatedTask.getStatus() != null) {
	        existingTask.setStatus(updatedTask.getStatus());
	    }
	    
	    

	    Task savedTask = taskRepository.save(existingTask);
	    taskResponse.setTask(savedTask);

	    /* ===============================
	       7️⃣ NOTIFICATIONS
	       =============================== */
	    if (reassigned) {
	        // Notify new employee
	        Notification empNotification = new Notification();
	        empNotification.setType(NotificationType.TASK_ASSIGNED);
	        empNotification.setEmployeeId(savedTask.getAssignedToUserId());
	        empNotification.setMessage(
	                "New task assigned: " + savedTask.getTaskName());

	        empNotification.setEmployeeId(savedTask.getAssignedToUserId());
	        empNotification.setSendToEmployee(true);
	        empNotification.setSendToAdmin(false);
	        empNotification.setSendToHR(false);

	        notificationService.saveNotification(empNotification);

	        // Notify admin
	        Notification adminNotification = new Notification();
	        adminNotification.setType(NotificationType.TASK_ASSIGNED);
	        adminNotification.setMessage(
	                loggedInUser.getUsername() +
	                " assigned a task to " +
	                savedTask.getAssignedTo());

	        adminNotification.setSendToAdmin(true);

	        notificationService.saveNotification(adminNotification);
	    }

	    /* ===============================
	       8️⃣ RESPONSE
	       =============================== */
	    response.setIsSuccess(true);
	    response.setStatus("Success");
	    response.setMessage(
	            responseCodeRepository.getMessageByCode(
	                    ResponseCode.TASK_UPDATE_SUCCESS, operation));
	    response.setData(taskResponse);

	    return response;
	}

	

		@Override
		public GenericResponse<String> deleteTaskById(Long id, String operation) {
			
			GenericResponse<String> response = new GenericResponse<>();
	
	        Task existingTask = taskRepository.findById(id).orElse(null);
	        if (existingTask != null) {
	        	
	        	 if (existingTask.getStatus() == TaskStatus.COMPLETED) {
	                 response.setIsSuccess(false);
	                 response.setStatus("Failed");
	                 response.setMessage("Completed task cannot be deleted");
	                 return response;
	             }
	        	
	            taskRepository.delete(existingTask);
	
	            String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_DELETE_SUCCESS, operation);
	            response.setIsSuccess(true);
	            response.setMessage(msg);
	            response.setStatus("Success");
	        } else {
	            String msg = responseCodeRepository.getMessageByCode(ResponseCode.TASK_DELETE_FAILED, operation);
	            response.setIsSuccess(false);
	            response.setMessage(msg);
	            response.setStatus("Failed");
	   
	        }
			return response;
		}


	 
	





}

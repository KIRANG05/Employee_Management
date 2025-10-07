package com.Practice.Employee.Management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Practice.Employee.Management.Modal.Task;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.TaskResponse;
import com.Practice.Employee.Management.Service.TaskService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PreAuthorize("hasRole('HR')")
	@PostMapping("/addTask")
	public ResponseEntity<TaskResponse> addTask(@Valid @RequestBody Task task, HttpServletRequest request){
		
		String operation = request.getRequestURI();
		TaskResponse response = taskService.addTask(task,operation);
		
		if (response.getIsSuccess()){
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(response);
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(response);
		}	
	}
	
	
	@PreAuthorize("hasAnyRole('HR', 'ADMIN')")
	@GetMapping("/fetchAllTasks")
	public ResponseEntity<TaskResponse> getAllTasks(HttpServletRequest request){
		
		String operation = request.getRequestURI();
		TaskResponse response = taskService.getAllTasks(operation);
		
		if(response != null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
			
		}else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	@GetMapping("/filter")
	public ResponseEntity<TaskResponse> filterTasks(@RequestParam(required = false) String fromDateStr, 
													@RequestParam(required = false) String toDateStr,
													@RequestParam(required = false) String assignedBy,
													@RequestParam(required = false) String assignedTo, HttpServletRequest request) {
		String operation = request.getRequestURI();
		TaskResponse response = taskService.filterTasks(fromDateStr, toDateStr, assignedBy, assignedTo, operation);
		if(response.getIsSuccess()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(response);
			
		}else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(response);
		}
		
	}
	
	
	

}

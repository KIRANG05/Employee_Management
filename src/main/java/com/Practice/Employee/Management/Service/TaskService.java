package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.Modal.Task;
import com.Practice.Employee.Management.ResponseModal.TaskResponse;


public interface TaskService {

	TaskResponse addTask(Task task, String operation);

	TaskResponse getAllTasks(String operation);

	TaskResponse filterTasks(String fromDateStr, String toDateStr, String assignedBy, String assignedTo, String operation);

}

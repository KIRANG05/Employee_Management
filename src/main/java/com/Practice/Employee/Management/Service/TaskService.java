package com.Practice.Employee.Management.Service;

import com.Practice.Employee.Management.Modal.Task;
import com.Practice.Employee.Management.Modal.TaskStatus;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.TaskResponse;

import jakarta.servlet.http.HttpServletRequest;


public interface TaskService {

	GenericResponse<TaskResponse> addTask(Task task, String operation);

	GenericResponse<TaskResponse> getAllTasks(String operation);

	GenericResponse<TaskResponse> filterTasks(String fromDateStr, String toDateStr, String assignedBy, String assignedTo, TaskStatus status, String operation);

	GenericResponse<TaskResponse> getTaskById(Long id, String operation);

	GenericResponse<TaskResponse> updateTask(Long id, Task task, String operation);

	GenericResponse<String> deleteTaskById(Long id, String operation);

}

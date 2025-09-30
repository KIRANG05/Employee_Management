package com.Practice.Employee.Management.ResponseModal;

import java.util.List;

import com.Practice.Employee.Management.Modal.Task;

public class TaskResponse extends GenericResponse {

	private Task task;
	private List<Task> tasks;
	public TaskResponse() {

	}

	public TaskResponse(Task task, List<Task> tasks) {
		this.task = task;
		this.tasks = tasks;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}


	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "TaskResponse [task=" + task + ", tasks=" + tasks + "]";
	}


}

package com.Practice.Employee.Management.Modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_details")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String taskName;
	private String description;
	private String assignedBy;
	private String assignedTo;
	private String status;
	private LocalDate assignedDate;
	private LocalDate dueDate;

	/* if we define parameterized contsructor java dy defautl will not provide no argument
	connstructor so we have to explicitly declre the constructor of no arguments */
	public Task() {

	}

	public  Task(Long id, String taskName, String description,String assignedBy, String assignedTo, String status,  LocalDate assignedDate, LocalDate dueDate  ) {

		this.id = id;
		this.taskName = taskName;
		this.description = description;
		this.assignedBy = assignedBy;
		this.assignedTo = assignedTo;
		this.status = status;
		this.assignedDate = assignedDate;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", description=" + description + ", assignedBy="
				+ assignedBy + ", assignedTo=" + assignedTo + ", status=" + status + ", assignedDate=" + assignedDate
				+ ", dueDate=" + dueDate + "]";
	}



}

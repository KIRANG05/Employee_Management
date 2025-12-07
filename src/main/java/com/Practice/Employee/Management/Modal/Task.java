package com.Practice.Employee.Management.Modal;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "task_details")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@NotEmpty(message = "* Task Name Required")
	@Size(max = 100, message = "Task name must be less than 100 characters")
	private String taskName;
	@Size(max = 500, message ="Description cannot exceed 500 characters")
	private String description;
	@Column(nullable = false)
	private String assignedBy;
	@Column(nullable = false)
	@NotEmpty(message = "* AssignedTo is Required")
	private String assignedTo;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TaskStatus status = TaskStatus.PENDING ;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate assignedDate = LocalDate.now();
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dueDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "Task_Priority", nullable = false)
	private TaskPriority priority;

	/* if we define parameterized contsructor java dy defautl will not provide no argument
	connstructor so we have to explicitly declre the constructor of no arguments */
	public Task() {

	}

	public  Task(Long id, String taskName, String description,String assignedBy, String assignedTo, TaskStatus status,  LocalDate assignedDate, LocalDate dueDate  ) {

		this.id = id;
		this.taskName = taskName;
		this.description = description;
		this.assignedBy = assignedBy;
		this.assignedTo = assignedTo;
		this.status = (status != null) ? status : TaskStatus.PENDING;
		this.assignedDate = LocalDate.now();
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

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
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
	
	

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskName=" + taskName + ", description=" + description + ", assignedBy="
				+ assignedBy + ", assignedTo=" + assignedTo + ", status=" + status + ", assignedDate=" + assignedDate
				+ ", dueDate=" + dueDate + ", priority=" + priority + "]";
	}



}

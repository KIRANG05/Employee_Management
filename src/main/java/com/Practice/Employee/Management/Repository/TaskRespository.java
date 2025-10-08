package com.Practice.Employee.Management.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Practice.Employee.Management.Modal.Task;
import com.Practice.Employee.Management.Modal.TaskStatus;

@Repository
public interface TaskRespository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task>{
	Long countByStatus(TaskStatus completed);

	@Query("SELECT COUNT(t) FROM Task t WHERE t.dueDate < :today AND t.status <> :completedStatus")
	Long countOverdue(@Param("today") LocalDate today, @Param("completedStatus") TaskStatus completedStatus);

	Long countByAssignedTo(String assignedTo);

	Long countByAssignedToAndStatus(String assignedTo, TaskStatus status);

	@Query("SELECT COUNT(t) FROM Task t WHERE t.assignedTo = :assignedTo AND t.dueDate < :today AND t.status <> :completedStatus")
	Long countOverdueByEmployee(@Param("assignedTo") String assignedTo, @Param("today")LocalDate today, @Param("completedStatus") TaskStatus completedStatus);


}

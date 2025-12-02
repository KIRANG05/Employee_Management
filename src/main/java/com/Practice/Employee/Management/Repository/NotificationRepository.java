package com.Practice.Employee.Management.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Practice.Employee.Management.Modal.Notification;

import jakarta.transaction.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
//	List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
//	Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
	Page<Notification> findByEmployeeIdOrderByCreatedAtDesc(Long id, Pageable pageable);
	Page<Notification> findByHrIdOrderByCreatedAtDesc(Long id, Pageable pageable);
	Page<Notification> findBySendToAdminOrderByCreatedAtDesc(boolean b, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Notification n SET n.isRead = true WHERE n.employeeId = :userId")
	void markAllAsReadForEmployee(Long userId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Notification n SET n.isRead = true WHERE n.hrId = :userId")
	void markAllAsReadForHR(Long userId);

	@Transactional
	@Modifying
	@Query("UPDATE Notification n SET n.isRead = true WHERE n.sendToAdmin = true")
	void markAllAsReadForAdmin();




}

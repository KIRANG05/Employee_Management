package com.Practice.Employee.Management.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Practice.Employee.Management.Modal.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
//	List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
//	Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
	Page<Notification> findByEmployeeIdOrderByCreatedAtDesc(Long id, Pageable pageable);
	Page<Notification> findByHrIdOrderByCreatedAtDesc(Long id, Pageable pageable);
	Page<Notification> findBySendToAdminOrderByCreatedAtDesc(boolean b, Pageable pageable);



}

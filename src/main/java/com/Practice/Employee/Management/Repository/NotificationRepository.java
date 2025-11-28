package com.Practice.Employee.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Practice.Employee.Management.Modal.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}

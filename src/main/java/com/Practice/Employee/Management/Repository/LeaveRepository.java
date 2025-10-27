package com.Practice.Employee.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Practice.Employee.Management.Modal.LeaveRequest;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

}

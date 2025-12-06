package com.Practice.Employee.Management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Practice.Employee.Management.Modal.LeaveRequest;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

	List<LeaveRequest> findByEmployeeId(Long employeeId);

}

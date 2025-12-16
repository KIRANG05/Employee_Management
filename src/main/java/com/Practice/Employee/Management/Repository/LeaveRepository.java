package com.Practice.Employee.Management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Practice.Employee.Management.Modal.LeaveRequest;
import com.Practice.Employee.Management.Modal.LeaveStatus;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

	List<LeaveRequest> findByEmployeeId(Long employeeId);

	Long countByStatus(LeaveStatus pending);

	@Query("""
			SELECT COUNT(l)
			FROM LeaveRequest l
			WHERE l.status = 'APPROVED'
			AND CURRENT_DATE BETWEEN l.fromDate AND l.toDate
			""")
	long countTodayOnLeave();

}

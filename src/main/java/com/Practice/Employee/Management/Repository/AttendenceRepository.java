package com.Practice.Employee.Management.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Practice.Employee.Management.Modal.Attendence;

public interface AttendenceRepository extends JpaRepository<Attendence, Long>{

	@Query("SELECT a FROM Attendence a WHERE a.employee.username = :username AND a.date = :date")
	Optional<Attendence> findByEmployeeUsernameAndDate(String username, LocalDate today);

	@Query("SELECT a FROM Attendence a WHERE a.employee.id = :empId AND YEAR(a.date) = :year AND MONTH(a.date) = :month")
	List<Attendence> findAllByEmployeeMonth(Long empId, int year, int month);

}

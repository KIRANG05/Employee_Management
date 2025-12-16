package com.Practice.Employee.Management.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Practice.Employee.Management.Modal.Attendence;

public interface AttendenceRepository extends JpaRepository<Attendence, Long>{

//	@Query("SELECT a FROM Attendence a WHERE a.employee.username = :username AND a.date = :date")
//	Optional<Attendence> findByEmployeeUsernameAndDate(
//	        @Param("username") String username,
//	        @Param("date") LocalDate date
//	);
	
//	Optional<Attendence> findFirstByEmployeeIdAndDate(Long employeeId, LocalDate date);
	
//	@Query("SELECT a FROM Attendence a WHERE a.employee.id = :empId AND a.date = :date ORDER BY a.id ASC")
//	List<Attendence> findTodayRecord(@Param("empId") Long empId, @Param("date") LocalDate date);

	Optional<Attendence> findFirstByUserIdAndDate(Long userId, LocalDate date);



	@Query("SELECT a FROM Attendence a WHERE a.user.id = :userId AND YEAR(a.date) = :year AND MONTH(a.date) = :month")
	List<Attendence> findAllByUserMonth(Long userId, int year, int month);


	
	@Query("""
			SELECT COUNT(a)
			FROM Attendence a
			WHERE DATE(a.loginTime) = CURRENT_DATE
			""")
	long countTodayAttendance();


}

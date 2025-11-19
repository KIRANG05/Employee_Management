package com.Practice.Employee.Management.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Practice.Employee.Management.Modal.Attendence;

public interface AttendenceRepository extends JpaRepository<Attendence, Long>{

	Optional<Attendence> findByEmployeeUsernameAndDate(String username, LocalDate today);

}

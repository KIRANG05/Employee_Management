package com.Practice.Employee.Management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Practice.Employee.Management.Modal.Employee;
import com.Practice.Employee.Management.Modal.Users;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByname(String assignedTo);

	Optional<Employee> findByUser_Id(Long userId);

//	Optional<Users> findByUser_Id(Long userId);

}

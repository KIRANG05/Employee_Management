package com.Practice.Employee.Management.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Practice.Employee.Management.Modal.Role;
import com.Practice.Employee.Management.Modal.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByUsername(String username);

	List<Users> findUsersByRole(Role roleHr);

	boolean existsByUsername(String username);

}

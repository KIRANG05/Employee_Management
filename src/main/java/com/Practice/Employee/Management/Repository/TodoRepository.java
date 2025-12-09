package com.Practice.Employee.Management.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Practice.Employee.Management.Modal.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findByTitleContainingIgnoreCaseAndCreatedAt(String search, LocalDate date);

	List<Todo> findByTitleContainingIgnoreCase(String search);

	List<Todo> findByCreatedAt(LocalDate date);

}

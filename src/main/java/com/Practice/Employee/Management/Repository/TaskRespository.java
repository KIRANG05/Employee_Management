package com.Practice.Employee.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.Practice.Employee.Management.Modal.Task;

@Repository
public interface TaskRespository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task>{

}

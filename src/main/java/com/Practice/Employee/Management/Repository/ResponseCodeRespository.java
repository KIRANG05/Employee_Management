package com.Practice.Employee.Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Practice.Employee.Management.Modal.ResponseMessages;


@Repository
public interface ResponseCodeRespository extends JpaRepository<ResponseMessages, Long> {

	
	@Query("SELECT r.message FROM ResponseMessages r WHERE r.code = :code AND :operation LIKE CONCAT(r.operation, '%')")
	String getMessageByCode(@Param("code") String code, @Param("operation") String operation);

	ResponseMessages findByCodeAndOperation(String code, String operation);

}

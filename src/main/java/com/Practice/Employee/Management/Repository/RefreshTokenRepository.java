package com.Practice.Employee.Management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Practice.Employee.Management.Modal.RefreshToken;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByTokenAndIsDeletedFalse(String refreshToken);

	

	

}

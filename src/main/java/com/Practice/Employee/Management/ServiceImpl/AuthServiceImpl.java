package com.Practice.Employee.Management.ServiceImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Practice.Employee.Management.Modal.AuthRequest;
import com.Practice.Employee.Management.Modal.RefreshToken;
import com.Practice.Employee.Management.Modal.Users;
import com.Practice.Employee.Management.Repository.RefreshTokenRepository;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Repository.UserRepository;
import com.Practice.Employee.Management.ResponseModal.GenericResponse;
import com.Practice.Employee.Management.ResponseModal.UserResponse;
import com.Practice.Employee.Management.Security.CustomUserDetails;
import com.Practice.Employee.Management.Security.JwtService;
import com.Practice.Employee.Management.Service.AuthService;
import com.Practice.Employee.Management.utils.ResponseCode;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResponseCodeRespository responseCode;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	public AuthServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
			JwtService jwtService) {
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public UserResponse registerUser(Users user, String operation) {

		UserResponse response = new UserResponse();

		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			String msg = responseCode.getMessageByCode(ResponseCode.USERNAME_ALREADY_EXISTS, operation);
			response.setIsSuccess(false);
			response.setMessage(msg);
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			Users result = userRepository.save(user);
			String msg = responseCode.getMessageByCode(ResponseCode.USER_REGISTER_SUCCESS, operation);
			response.setIsSuccess(true);
			response.setStatus("Success");
			response.setMessage(msg);
			response.setRole(result.getRole().name());
			response.setUsername(result.getUsername());
		}
		return response;
	}

	@Override
	public UserResponse login(AuthRequest authrequest, String operation) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		Users user = userDetails.getUser();

		String token = jwtService.generateToken(user.getUsername());
		String refreshToken = jwtService.generateRefreshToken(user.getUsername());

		// save refresh token in DB
		RefreshToken tokenEntity = new RefreshToken(userDetails.getUsername(), refreshToken,
				Instant.now().plus(Duration.ofDays(7)));
		refreshTokenRepository.save(tokenEntity);

		UserResponse response = new UserResponse();
		String msg = responseCode.getMessageByCode(ResponseCode.LOGIN_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setMessage(msg);
		response.setStatus("Success");
		response.setAccessToken(token);
		response.setRefreshToken(refreshToken);
		response.setUsername(user.getUsername());
		response.setRole(user.getRole().name());
		return response;
	}

	@Override
	public UserResponse refreshAccessToken(String refreshToken, String operation) {
		Optional<RefreshToken> tokenEntity = refreshTokenRepository.findByTokenAndIsDeletedFalse(refreshToken);

		if (tokenEntity.isPresent()) {
			RefreshToken validRefreshToken = tokenEntity.get();

			if (validRefreshToken.getExpiryDate().isBefore(Instant.now())) {
				throw new RuntimeException("Refresh token expired!");
			}

			// valid refresh token, issue new access token
			String newAccessToken = jwtService.generateToken(validRefreshToken.getUsername());

			UserResponse response = new UserResponse();
			String msg = responseCode.getMessageByCode(ResponseCode.NEW_ACCESS_TOKEN_GENERATE_SUCCESS, operation);
			response.setAccessToken(newAccessToken);
			response.setMessage(msg);
			response.setIsSuccess(true);
			response.setStatus("Success");
			return response;

		} else {
			throw new RuntimeException("Invalid refresh token!");
		}

	}

	@Override
	public GenericResponse logout(String refreshToken, String operation) {
		
		 Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByTokenAndIsDeletedFalse(refreshToken);

		    if (tokenOpt.isPresent()) {
		        RefreshToken token = tokenOpt.get();
		        token.setIsDeleted(true);
		        refreshTokenRepository.save(token);
		    }

		
		GenericResponse response = new GenericResponse();
		String msg = responseCode.getMessageByCode(ResponseCode.LOGOUT_SUCCESS, operation);
		response.setIsSuccess(true);
		response.setStatus("Success");
		response.setMessage(msg);
		return response;
	}

}

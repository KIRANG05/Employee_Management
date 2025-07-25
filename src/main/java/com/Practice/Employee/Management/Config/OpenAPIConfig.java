package com.Practice.Employee.Management.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
		name = "BearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT")
public class OpenAPIConfig {
	
		@Bean
		public OpenAPI myOpenApi() {
			return new OpenAPI()
					.info(new Info().title("Employee Management API")
							.description("This API is designed for managing employee data, roles, and operations. "
									+ "It provides secure access using Spring Security and supports role-based access for Admin, HR, and Employee users. "
									+ "Built using Spring Boot, the API supports standard CRUD operations, authentication, and profile management.")
							.version("1.0.0"));
		}

}

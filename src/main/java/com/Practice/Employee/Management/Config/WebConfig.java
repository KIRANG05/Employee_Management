package com.Practice.Employee.Management.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${employee.images.path}")
	private String imagePath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Map any request starting with /images/ to the folder specified in your properties file
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + imagePath);
	}

}

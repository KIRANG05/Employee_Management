package com.Practice.Employee.Management.Config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

	
	
	 @Bean
	 public RedisCacheConfiguration redisCacheConfiguration() {
	   return RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofHours(6))   // cache time for messages
	                .disableCachingNullValues();
	    }
}

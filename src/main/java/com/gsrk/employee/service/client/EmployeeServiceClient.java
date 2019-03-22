package com.gsrk.employee.service.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeServiceClient 
{
    public static void main( String[] args )
    {
        SpringApplication.run(EmployeeServiceClient.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}

package org.bojarski.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User Authentication and Authorization Server for Task Management System.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UaaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(UaaServerApplication.class, args);
	}
}

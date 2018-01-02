package org.bojarski.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Tasks Service for Tasks Management System.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@SpringBootApplication
public class TasksService {
	public static void main(String[] args) {
		SpringApplication.run(TasksService.class, args);
	}
}

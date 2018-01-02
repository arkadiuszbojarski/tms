/**
 * 
 */
package org.bojarski.tasks.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Global exception handler class.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	/**
	 * Method handling {@link NoSuchElementException} thrown when accessing non-existing resources.
	 * 
	 * @param {@link NoSuchElementException} thrown when accessing non-existing resources.
	 * 
	 * @return {@link ResponseEntity} with HTTP status code 404 - Not Found.
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> handle(NoSuchElementException exception) {
		return ResponseEntity.notFound().build();
	}
}

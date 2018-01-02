/**
 * 
 */
package org.bojarski.uaa.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import javax.validation.Valid;

import org.bojarski.uaa.model.Account;
import org.bojarski.uaa.resource.AccountResource;
import org.bojarski.uaa.resource.AccountResourceAssembler;
import org.bojarski.uaa.service.AccountService;
import org.bojarski.uaa.validation.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * User registration controller.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

	/**
	 * {@link AccountService} implementation instance.
	 */
	private final AccountService service;
	
	/**
	 * {@link AccountValidator} implementation instance.
	 */
	private final AccountValidator validator;
	
	/**
	 * {@link AccountResourceAssembler} implementation instance.
	 */
	private final AccountResourceAssembler assembler;

	/**
	 * Binding AccountValidator instance for client input validation.
	 * 
	 * @param {@link WebDataBinder}
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	/**
	 * Request mapping for retriving currently authenticated principal details.
	 * 
	 * @param principal a currently authenticated {@link Principal}.
	 * @return currently authenticated {@link Principal}.
	 */
	@GetMapping("/users/me")
	public Principal user(final Principal principal) {
		return principal;
	}

	/**
	 * Request mapping for registering new user.
	 * 
	 * @param an {@link Account} form to be registered.
	 * 
	 * @return registered @{link {@link AccountResource}
	 * @throws {@link URISyntaxException} thrown when failing to create location URI for registered account.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@Valid @RequestBody Account account) throws URISyntaxException {
		AccountResource resource = assembler.toResource(service.register(account));
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.location(new URI(resource.getLink("self").getHref()))
				.body(resource);
	}
}

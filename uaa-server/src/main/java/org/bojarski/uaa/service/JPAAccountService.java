/**
 * 
 */
package org.bojarski.uaa.service;

import java.util.Arrays;
import java.util.HashSet;

import org.bojarski.uaa.model.Account;
import org.bojarski.uaa.model.Roles;
import org.bojarski.uaa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * JPA based implementation of {@link AccountService} interface.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JPAAccountService implements AccountService {

	/** {@link AccountRepository} instance. */
	private final AccountRepository repository;
	
	/** {@link BCryptPasswordEncoder} for encoding account passwords. */
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bojarski.uaa.service.AccountService#register(org.bojarski.uaa.model.
	 * Account)
	 */
	@Override
	public Account register(Account form) {
		Account account = new Account();
		account.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
		account.setRoles(new HashSet<>(Arrays.asList(Roles.USER)));
		account.setUsername(form.getUsername());
		return repository.save(account);
	}

}

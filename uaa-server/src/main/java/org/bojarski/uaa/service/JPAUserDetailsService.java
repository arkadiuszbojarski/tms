/**
 * 
 */
package org.bojarski.uaa.service;

import java.util.Optional;

import org.bojarski.uaa.Messages;
import org.bojarski.uaa.model.Account;
import org.bojarski.uaa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author Arkadiusz Bojarski
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JPAUserDetailsService implements UserDetailsService {

	private final AccountRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO: Remove
		if ("admin".equals(username)) {
			return User.withUsername("admin").password("$2a$10$7V4v7bIfKWwHppPeOeBuOOJL3DHaDX6jFHI9iOqB4i9d6NJkSufmO")
					.roles("ADMIN").build();
		}
		Optional<Account> account = Optional.ofNullable(repository.findOne(username));
		return createUserFromAccount(account.orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND)));
	}

	/**
	 * Method allowing creating {@link UserDetails} from domain entity {@link Account}.
	 * 
	 * @param account an {@link Account} for which {@link UserDetails} are to be created.
	 * 
	 * @return {@link UserDetails} created for provided {@link Account}.
	 */
	private UserDetails createUserFromAccount(Account account) {
		return User.withUsername(account.getUsername())
				.password(account.getPassword())
				.roles(account.getRoles().stream().map(role -> role.name()).toArray(String[]::new))
				.build();
	}

}

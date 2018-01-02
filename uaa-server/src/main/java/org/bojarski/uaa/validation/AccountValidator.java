/**
 * 
 */
package org.bojarski.uaa.validation;

import org.bojarski.uaa.model.Account;
import org.bojarski.uaa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;

/**
 * {@link Account} validator class implementing {@link Validator} interface.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountValidator implements Validator {

	/** {@link AccountRepository} implementation instance. */
	private final AccountRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Account.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Account account = (Account) target;

		if (!account.getPassword().equals(account.getConfirm())) {
			errors.rejectValue("confirm", "confirm.do.not.match.password");
		}
		
		if (repository.exists(account.getUsername())) {
			errors.rejectValue("username", "username.exists");
		}
	}
}

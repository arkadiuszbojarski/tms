/**
 * 
 */
package org.bojarski.uaa.service;

import org.bojarski.uaa.model.Account;

/**
 * @{link Account} Service interface.
 * 
 * @author Arkadiusz Bojarski
 *
 */
public interface AccountService {
	
	/**
	 * Method allowing for registering new user {@link Account}.
	 * 
	 * @param an {@link Account} to be registered.
	 * 
	 * @return registered {@link Account}.
	 * 
	 */
	Account register(Account account);
}

/**
 * 
 */
package org.bojarski.uaa.resource;

import org.bojarski.uaa.controller.RegistrationController;
import org.bojarski.uaa.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Arkadiusz Bojarski
 *
 */
@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource> {

	@Autowired
	private EntityLinks entityLinks;

	/**
	 * @param controllerClass
	 * @param resourceType
	 */
	public AccountResourceAssembler() {
		super(RegistrationController.class, AccountResource.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.hateoas.mvc.ResourceAssemblerSupport#instantiateResource(
	 * java.lang.Object)
	 */
	@Override
	protected AccountResource instantiateResource(Account entity) {
		return AccountResource.builder().username(entity.getUsername()).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.hateoas.ResourceAssembler#toResource(java.lang.Object)
	 */
	@Override
	public AccountResource toResource(Account entity) {
		AccountResource resource = instantiateResource(entity);
		LinkBuilder builder = entityLinks.linkForSingleResource(entity.getClass(), entity.getUsername());
		resource.add(builder.withSelfRel());
		resource.add(builder.withRel("account"));
		return resource;
	}

}

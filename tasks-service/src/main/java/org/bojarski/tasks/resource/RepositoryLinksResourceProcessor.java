/**
 * 
 */
package org.bojarski.tasks.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.bojarski.tasks.controller.TaskController;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

/**
 * Repository links processor class.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Component
public class RepositoryLinksResourceProcessor implements ResourceProcessor<RepositoryLinksResource> {

	/* (non-Javadoc)
	 * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
	 */
	@Override
	public RepositoryLinksResource process(RepositoryLinksResource resource) {
		resource.add(linkTo(methodOn(TaskController.class).search(null, null, null, null, null)).withRel("tasks"));
		return resource;
	}

}

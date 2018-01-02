/**
 * 
 */
package org.bojarski.tasks.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.bojarski.tasks.controller.TaskController;
import org.bojarski.tasks.model.Task;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Task assebler class extending {@link ResourceAssemblerSupport}.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Component
public class TaskAssembler extends ResourceAssemblerSupport<Task, TaskResource> {

	public TaskAssembler() {
		super(TaskController.class, TaskResource.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.hateoas.ResourceAssembler#toResource(java.lang.Object)
	 */
	@Override
	public TaskResource toResource(Task entity) {
		TaskResource resource = new TaskResource(entity);
		resource.add(linkTo(TaskController.class, entity.getAuthor()).slash(entity.getId()).withSelfRel());
		resource.add(linkTo(TaskController.class, entity.getAuthor()).slash(entity.getId()).withRel("task"));
		return resource;
	}
}

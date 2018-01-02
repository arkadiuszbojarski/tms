/**
 * 
 */
package org.bojarski.tasks.resource;

import org.bojarski.tasks.model.Task;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Task resource class.
 * 
 * @author Arkadiusz Bojarski
 *
 */
public class TaskResource extends Resource<Task> {

	/**
	 * @param content
	 * @param links
	 */
	public TaskResource(Task content, Link... links) {
		super(content, links);
	}
}

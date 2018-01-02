/**
 * 
 */
package org.bojarski.tasks.service;

import org.bojarski.tasks.model.Task;
import org.bojarski.tasks.resource.CreateForm;
import org.bojarski.tasks.resource.TaskResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

/**
 * Task service interface.
 * 
 * @author Arkadiusz Bojarski
 *
 */
public interface TaskService {

	/**
	 * Method allowing creating of new {@link Task}.
	 * 
	 * @param {@link
	 * 			Task} to be created.
	 * 
	 * @return created {@link Task}.
	 */
	Iterable<Task> create(CreateForm form);

	/**
	 * Method allowing updating existing {@link Task}.
	 * 
	 * @param {@link
	 * 			Task} to be updated.
	 * @param updated
	 *            {@link Task} form.
	 * 
	 * @return a {@link TaskResource} of updated {@link Task}.
	 */
	Task update(Task old, Task form);

	/**
	 * Method allowing searching {@link Task}s.
	 * 
	 * @param a
	 *            QueryDsl {@link Predicate} instance.
	 * @param a
	 *            {@link Pageable} information.
	 * 
	 * @return a {@link Page} containing found {@link Task}s.
	 */
	Page<Task> search(Predicate predicate, Pageable pageable);

	/**
	 * Method allowing editing existing {@link Task}.
	 * 
	 * @param a
	 *            {@link Task} to be edited.
	 * @param an
	 *            edited {@link Task} form.
	 * 
	 * @return an edited {@link Task}.
	 */
	Task edit(Task old, Task form);

	/**
	 * Method allowing deleting existing {@link Task}.
	 * 
	 * @param a
	 *            {@link Task} to be deleted.
	 */
	void delete(Task deleted);
}

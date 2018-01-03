/**
 * 
 */
package org.bojarski.tasks.controller;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bojarski.tasks.model.QTask;
import org.bojarski.tasks.model.Task;
import org.bojarski.tasks.resource.CreateForm;
import org.bojarski.tasks.resource.TaskAssembler;
import org.bojarski.tasks.resource.TaskResource;
import org.bojarski.tasks.service.TaskService;
import org.bojarski.tasks.validation.CreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;

/**
 * @{link Task} Controller class.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {

	/** {@link TaskService} implementation instance. */
	private final TaskService taskService;

	/** {@link TaskAssembler} instance. */
	private final TaskAssembler taskAssembler;

	/** {@link PagedResourcesAssembler} instance for Task class. */
	private final PagedResourcesAssembler<Task> pagedTaskAssemble;
	
	/** {@link CreateFormValidator} instance. */
	private final CreateFormValidator validator;
	
	/**
	 * Binding CreateFormValidator instance for client input validation.
	 * 
	 * @param binder {@link WebDataBinder}
	 */
	@InitBinder(value = "create")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	/**
	 * Request mapping for creating new {@link Task}.
	 * 
	 * @param form a {@link Task} to be created.
	 * 
	 * @return {@link ResponseEntity} containing created {@link Task} resource.
	 * @throws {@link URISyntaxException} thrown when failing to create location URI for created {@link Task}.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody final CreateForm form) throws URISyntaxException {
		List<?> resources = taskAssembler.toResources(taskService.create(form));

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(resources);
	};

	/**
	 * Request mapping for retrieving user {@link Task}s.
	 * 
	 * @param pageable
	 *            a {@link Pageable} information created from request query.
	 * @param initial
	 *            a QueryDsl {@link Predicate} created from request query.
	 * @param principal
	 *            current Spring {@link Principal}.
	 * 
	 * @return a {@link ResponseEntity} containing {@link PagedResources} of user
	 *         {@link Task}s.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> search(
			@PageableDefault(size = 20, sort = "title", direction = Direction.ASC) final Pageable pageable,
			@QuerydslPredicate(root = Task.class) final Predicate initial,
			@RequestParam(required = false, name = "day") @DateTimeFormat(pattern="yyyy-MM-dd") final Date day,
			final Principal principal) {
		QTask task = QTask.task;
		BooleanBuilder predicate = new BooleanBuilder(initial);

		predicate.and(task.author.eq(principal.getName()));
		Optional.ofNullable(day).ifPresent(forDay -> {
			predicate.and(task.start.before(day));
			predicate.and(task.deadline.isNull().or(task.deadline.after(day)));
		});
		Page<Task> tasks = taskService.search(predicate, pageable);

		return ResponseEntity.ok(tasks.hasContent() ? pagedTaskAssemble.toResource(tasks, taskAssembler)
				: pagedTaskAssemble.toEmptyResource(tasks, Task.class, null));
	}

	/**
	 * Request mapping for retrieving single {@link Task}.
	 * 
	 * @param optional
	 *            an {@link Optional} containing a {@link Task} to be returned.
	 * @param principal
	 *            current Spring {@link Principal}.
	 * 
	 * @return a {@link ResponseEntity} containing a {@link TaskResource} with
	 *         provided id.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> read(@PathVariable("id") final Optional<Task> optional, final Principal principal) {
		Task retrieved = optional.filter(task -> principal.getName().equals(task.getAuthor())).get();
		TaskResource resource = taskAssembler.toResource(retrieved);
		return ResponseEntity.ok(resource);
	};

	/**
	 * Request mapping for updating existing {@link Task}.
	 * 
	 * @param form
	 *            updated {@link Task}.
	 * @param optional
	 *            an @{link Optional} containing a {@link Task} to be updated.
	 * @param principal
	 *            current Spring {@link Principal}.
	 * 
	 * @return a {@link ResponseEntity} containing updated {@link TaskResource}.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@Valid @RequestBody Task form, @PathVariable("id") Optional<Task> optional,
			final Principal principal) {
		Task old = optional.filter(task -> principal.getName().equals(task.getAuthor())).get();
		Task updated = taskService.update(old, form);
		TaskResource resource = taskAssembler.toResource(updated);
		return ResponseEntity.ok(resource);
	};

	/**
	 * Request mapping for patching existing {@link Task}.
	 * 
	 * @param form
	 *            patches to {@link Task}.
	 * @param optional
	 *            an {@link Optional} containing a {@link Task} to patch.
	 * @param current
	 *            Spring {@link Principal}.
	 * 
	 * @return a {@link ResponseEntity} containing patched {@link TaskResource}.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> edit(@Valid @RequestBody Task form, @PathVariable("id") Optional<Task> optional,
			final Principal principal) {
		Task old = optional.filter(task -> principal.getName().equals(task.getAuthor())).get();
		Task updated = taskService.edit(old, form);
		TaskResource resource = taskAssembler.toResource(updated);
		return ResponseEntity.ok(resource);
	};

	/**
	 * Request mapping for deleting existing {@link Task}.
	 * 
	 * @param optional
	 *            an {@link Optional} containing a {@link Task} to be deleted.
	 * @param principal
	 *            current Spring {@link Principal}.
	 * 
	 * @return empty {@link ResponseEntity}.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Optional<Task> optional, final Principal principal) {
		Task deleted = optional.filter(task -> principal.getName().equals(task.getAuthor())).get();
		taskService.delete(deleted);
		return ResponseEntity.noContent().build();
	};

}

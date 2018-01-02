/**
 * 
 */
package org.bojarski.tasks.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.bojarski.tasks.Messages;
import org.bojarski.tasks.model.Task;
import org.bojarski.tasks.repository.TasksRepository;
import org.bojarski.tasks.resource.CreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;

/**
 * JPA based {@link TaskService} implementaion class.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JPATaskService implements TaskService {

	/** {@link TasksRepository} implementation instance. */
	private final TasksRepository repository;
	
	private void addPeriodToDate(Calendar date, CreateForm form) {
		Optional.ofNullable(form.getRepeatDays()).ifPresent(days -> date.add(Calendar.DATE, days));
		Optional.ofNullable(form.getRepeatMonths()).ifPresent(months -> date.add(Calendar.MONTH, months));
		Optional.ofNullable(form.getRepeatYears()).ifPresent(years -> date.add(Calendar.YEAR, years));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bojarski.tasks.service.TaskService#create(org.bojarski.tasks.model.Task)
	 */
	@Override
	public Iterable<Task> create(CreateForm form) {
		
		Calendar start = Calendar.getInstance();
		Calendar deadline = Calendar.getInstance();
		Calendar until = Calendar.getInstance();
		Integer days = form.getRepeatDays();
		Integer months = form.getRepeatMonths();
		Integer years = form.getRepeatYears();
		
		Optional.ofNullable(form.getStart()).ifPresent(date -> start.setTime(date));
		Optional.ofNullable(form.getDeadline()).ifPresent(date -> deadline.setTime(date));
		Optional.ofNullable(form.getRepeatUntil()).ifPresent(date -> until.setTime(date));
		
		if (days != null && days < 0) { throw new IllegalArgumentException(Messages.NEGATIVE_REPEAT_DAYS); }
		if (months != null && months < 0) { throw new IllegalArgumentException(Messages.NEGATIVE_REPEAT_MONTHS); }
		if (years != null && years < 0) { throw new IllegalArgumentException(Messages.NEGATIVE_REPEAT_YEARS); }
		
		int interval = ((days != null ? days : 0) + (months != null ? months : 0) + (years != null ? years : 0));
		if (interval <= 0) { form.setRepeatDays(1); }
		
		if (start.after(deadline)) { throw new IllegalArgumentException(Messages.START_AFTER_DEADLINE); }
		if (start.after(until)) { throw new IllegalArgumentException(Messages.START_AFTER_UNTIL); }
		
		List<Task> tasks = new ArrayList<>();
		
		while (!start.after(until)) {
			Task task = new Task();
			
			Optional.ofNullable(form.getDeadline()).ifPresent(date -> task.setDeadline(deadline.getTime()));
			task.setStart(start.getTime());
			task.setTitle(form.getTitle());
			
			tasks.add(task);
			
			addPeriodToDate(start, form);
			addPeriodToDate(deadline, form);
		}
		
		return repository.save(tasks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bojarski.tasks.service.TaskService#update(java.lang.Long,
	 * org.bojarski.tasks.model.Task)
	 */
	@Override
	public Task update(Task old, Task form) {
		old.setTitle(form.getTitle());
		old.setCompleted(form.getCompleted());
		return repository.save(old);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bojarski.tasks.service.TaskService#search(com.querydsl.core.types.
	 * Predicate, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Task> search(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bojarski.tasks.service.TaskService#edit(java.lang.Long,
	 * org.bojarski.tasks.model.Task)
	 */
	@Override
	public Task edit(Task old, Task form) {
		Optional.ofNullable(form.getTitle()).ifPresent(title -> old.setTitle(title));
		Optional.ofNullable(form.getCompleted()).ifPresent(completed -> old.setCompleted(completed));
		return repository.save(old);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bojarski.tasks.service.TaskService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Task task) {
		repository.delete(task);
	}

}

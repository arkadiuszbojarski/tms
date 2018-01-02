package org.bojarski.tasks.repository;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.bojarski.tasks.model.QTask;
import org.bojarski.tasks.model.Task;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;

/**
 * Task repository interface.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@RepositoryRestResource(exported = false)
public interface TasksRepository extends PagingAndSortingRepository<Task, Long>, QueryDslPredicateExecutor<Task>,
		QuerydslBinderCustomizer<QTask> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.querydsl.binding.QuerydslBinderCustomizer#customize(
	 * org.springframework.data.querydsl.binding.QuerydslBindings,
	 * com.querydsl.core.types.EntityPath)
	 */
	@Override
	default void customize(QuerydslBindings bindings, QTask root) {

		bindings.bind(String.class).all((StringPath path, Collection<? extends String> values) -> {
			BooleanBuilder predicate = new BooleanBuilder();
			values.forEach(value -> predicate.or(path.containsIgnoreCase(value)));
			return predicate;
		});

		bindings.bind(root.created).all((path, value) -> {
			Iterator<? extends Date> it = value.iterator();
			return path.between(it.next(), it.next());
		});
		
		bindings.bind(root.deadline).all((path, value) -> {
			Iterator<? extends Date> it = value.iterator();
			return path.between(it.next(), it.next());
		});
		
		bindings.bind(root.start).all((path, value) -> {
			Iterator<? extends Date> it = value.iterator();
			return path.between(it.next(), it.next());
		});
	}
}

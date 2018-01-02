/**
 * 
 */
package org.bojarski.uaa.repository;

import java.util.Collection;

import org.bojarski.uaa.model.Account;
import org.bojarski.uaa.model.QAccount;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;

/**
 * {@link Account} repository interface.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@RepositoryRestResource(path = "users")
public interface AccountRepository extends PagingAndSortingRepository<Account, String>,
		QueryDslPredicateExecutor<Account>, QuerydslBinderCustomizer<QAccount> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.querydsl.binding.QuerydslBinderCustomizer#customize(
	 * org.springframework.data.querydsl.binding.QuerydslBindings,
	 * com.querydsl.core.types.EntityPath)
	 */
	@Override
	default void customize(QuerydslBindings bindings, QAccount root) {
		bindings.bind(String.class).all((StringPath path, Collection<? extends String> values) -> {
			BooleanBuilder predicate = new BooleanBuilder();
			values.forEach(value -> predicate.or(path.containsIgnoreCase(value)));
			return predicate;
		});
	}

}

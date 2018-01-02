/**
 * 
 */
package org.bojarski.tasks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Auditing configuration class.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {

	@Bean
	public AuditorAware<String> provider() {
		return new SecurityAuditor();
	}

	@Bean
	public AuditingEntityListener listener() {
		return new AuditingEntityListener();
	}

	/**
	 * Security Auditor class implementing {@link AuditorAware} interfce.
	 * 
	 * @author Arkadiusz Bojarski
	 *
	 */
	public static class SecurityAuditor implements AuditorAware<String> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
		 */
		@Override
		public String getCurrentAuditor() {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}

	}
}

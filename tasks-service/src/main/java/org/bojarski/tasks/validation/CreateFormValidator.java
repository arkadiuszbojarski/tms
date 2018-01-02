/**
 * 
 */
package org.bojarski.tasks.validation;

import java.util.Date;

import org.bojarski.tasks.Messages;
import org.bojarski.tasks.resource.CreateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Arkadiusz Bojarski
 *
 */
@Component
public class CreateFormValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return CreateForm.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		CreateForm form = (CreateForm) target;

		Date start = form.getStart();
		Date deadline = form.getDeadline();
		Date until = form.getRepeatUntil();
		Integer days = form.getRepeatDays();
		Integer months = form.getRepeatMonths();
		Integer years = form.getRepeatYears();
		
		if (start == null) {
			if (deadline != null) {
				errors.rejectValue(Messages.START_FIELD, Messages.NO_START_FOR_DEADLINE);
			}

			if (until != null) {
				errors.reject(Messages.START_FIELD, Messages.NO_START_FOR_UNTIL);
			}
			
			if (days != null) {
				errors.reject(Messages.START_FIELD, Messages.NO_START_FOR_REPEAT);
			}
			
			if (months != null) {
				errors.reject(Messages.START_FIELD, Messages.NO_START_FOR_REPEAT);
			}
			
			if (years != null) {
				errors.reject(Messages.START_FIELD, Messages.NO_START_FOR_REPEAT);
			}
		} else {
			if (deadline != null && start.after(deadline)) {
				errors.rejectValue(Messages.START_FIELD, Messages.START_AFTER_DEADLINE);
			}

			if (until != null) {

				if (start.after(until)) {
					errors.rejectValue(Messages.START_FIELD, Messages.START_AFTER_UNTIL);
				}

				if (days != null && days < 0) {
					errors.rejectValue(Messages.REPEAT_DAYS_FILED, Messages.NEGATIVE_REPEAT_DAYS);
				}

				if (months != null && months < 0) {
					errors.rejectValue(Messages.REPEAT_MONTHS_FILED, Messages.NEGATIVE_REPEAT_MONTHS);
				}

				if (years != null && years < 0) {
					errors.rejectValue(Messages.REPEAT_YEARS_FILED, Messages.NEGATIVE_REPEAT_YEARS);
				}

				if (((days != null ? days : 0) + (months != null ? months : 0) + (years != null ? years : 0)) <= 0) {
					errors.rejectValue(Messages.REPEAT_FIELD, Messages.INVALID_REPEAT_PERIOD);
				}
			}
		}
	}
}

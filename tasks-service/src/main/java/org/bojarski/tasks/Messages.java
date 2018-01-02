/**
 * 
 */
package org.bojarski.tasks;

/**
 * @author Arkadiusz Bojarski
 *
 */
public class Messages {

	private Messages() { }
	
	public static final String START_FIELD = "start";
	public static final String REPEAT_FIELD = "repeat";
	public static final String REPEAT_DAYS_FILED = "repeat.days";
	public static final String REPEAT_MONTHS_FILED = "repeat.months";
	public static final String REPEAT_YEARS_FILED = "repeat.years";
	
	public static final String START_AFTER_DEADLINE = "start.after.deadline";
	public static final String START_AFTER_UNTIL = "start.after.repeat.until";
	public static final String NO_START_FOR_DEADLINE = "no.start.for.deadline";
	public static final String NO_START_FOR_UNTIL = "no.start.for.until";
	public static final String NO_START_FOR_REPEAT = "no.start.for.repeat";
	public static final String NO_REPEAT_FOR_UNTIL = "no.repeat.for.until";
	public static final String NEGATIVE_REPEAT_YEARS = "repeat.years.only.positive";
	public static final String NEGATIVE_REPEAT_MONTHS = "repeat.months.only.positive";
	public static final String NEGATIVE_REPEAT_DAYS = "repeat.days.only.positive";
	public static final String INVALID_REPEAT_PERIOD = "repeat.at.least.one.field.positive"; 


}

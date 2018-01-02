/**
 * 
 */
package org.bojarski.tasks.resource;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author Arkadiusz Bojarski
 *
 */
@Data
public class CreateForm {

	@NotBlank
	private String title;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadline;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date repeatUntil;

	@Min(value = 0)
	private Integer repeatDays;

	@Min(value = 0)
	private Integer repeatMonths;

	@Min(value = 0)
	private Integer repeatYears;

}

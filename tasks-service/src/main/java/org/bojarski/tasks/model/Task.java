/**
 * 
 */
package org.bojarski.tasks.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Domaing entity modeling task.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, unique = true, updatable = false)
	private Long id;

	@NotBlank
	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "COMPLETED", nullable = false)
	private Boolean completed = false;

	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date created;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START")
	private Date start;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEADLINE")
	private Date deadline;

	@CreatedBy
	@Column(name = "AUTHOR", nullable = false, updatable = false)
	private String author;

}

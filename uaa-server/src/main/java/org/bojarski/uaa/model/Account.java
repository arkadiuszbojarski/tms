/**
 * 
 */
package org.bojarski.uaa.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

/**
 * Domain Entity modeling user account.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Data
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@NotBlank
	@Column(name = "username", nullable = false, unique = true, updatable = true)
	private String username;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_name"))
	@Column(name = "user_role", nullable = false)
	private Set<Roles> roles = new HashSet<>(Arrays.asList(Roles.USER));

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;

	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private String confirm;

}

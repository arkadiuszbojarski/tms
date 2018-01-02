/**
 * 
 */
package org.bojarski.uaa.resource;

import org.springframework.hateoas.ResourceSupport;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @{link Account} resource class extending ResourceSupport.
 * 
 * @author Arkadiusz Bojarski
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class AccountResource extends ResourceSupport {
	private String username;
}

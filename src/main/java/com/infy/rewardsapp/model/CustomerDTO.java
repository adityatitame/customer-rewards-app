package com.infy.rewardsapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing customer input/output data.
 *
 * <p>This class is used for accepting and transferring customer-related data
 * between the client, controller, and service layers. It includes validation
 * annotations to ensure data integrity before processing.
 */
@Data
public class CustomerDTO {

	/** Unique identifier for the customer. Usually auto-generated. */
	private Integer customerId;
	
	/**
     * Full name of the customer.
     * <p>Must start with a capital letter and contain only alphabets. Can contain
     * multiple capitalized words (e.g., "John Doe").
     */
	@NotNull(message = "{customer.name.absent}")
	@Pattern(regexp = "^([A-Z][a-z]+)( [A-Z][a-z]+)*$", message = "{customer.name.invalid}")
	private String name;
	
	/**
     * Customer's 10-digit contact number.
     */
	@NotNull(message = "{customer.contact.absent}")
	@Pattern(regexp = "^\\d{10}$", message = "{customer.contact.invalid}")
	private String contact;
	
	/**
     * Valid email address of the customer.
     */
	@NotNull(message = "{customer.email.absent}")
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{customer.email.invalid}")
	private String email;
	
	/** Total reward points earned by the customer. */
	private Integer totalRewardPoints;
}

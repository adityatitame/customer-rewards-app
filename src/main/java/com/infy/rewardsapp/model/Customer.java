package com.infy.rewardsapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing a customer in the RewardsApp system.
 *
 * <p>This class is mapped to a database table and contains basic customer
 * information along with the total reward points earned.
 */
@Data
@Entity
public class Customer {
	
	/** Auto-generated unique identifier for the customer. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	
	/** Name of the customer. */
	private String name;
	
	/** Contact number of the customer. */
	private String contact;
	
	/** Email address of the customer. */
	private String email;
	
	/** Total reward points accumulated by the customer. */
	private Integer totalRewardPoints;
}

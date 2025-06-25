package com.infy.rewardsapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Entity representing a customer in the RewardsApp system.
 *
 * <p>
 * This class is mapped to a database table and contains basic customer
 * information along with the total reward points earned.
 */
@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	private String name;
	private String contact;
	private String email;
	private Integer totalRewardPoints;
}

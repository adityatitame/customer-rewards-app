package com.infy.rewardsapp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Entity representing a transaction made by a customer.
 *
 * <p>
 * Each transaction records the amount spent, date of purchase, and the reward
 * points earned. It is linked to a {@link Customer}.
 */
@Data
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	private LocalDate date;
	private Double amount;
	private Integer rewardPoints;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}

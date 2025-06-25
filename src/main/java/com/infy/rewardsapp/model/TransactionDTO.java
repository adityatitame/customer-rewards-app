package com.infy.rewardsapp.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing transaction data exchanged between
 * client, controller, and service layers.
 *
 * <p>
 * Includes basic transaction information, customer reference, and optional
 * reward points. Validation is applied on the amount field to ensure
 * correctness.
 */
@Data
public class TransactionDTO {

	private Integer transactionId;
	private LocalDate date;

	@NotNull(message = "{transaction.amount.absent}")
	@Min(value = 0, message = "{transaction.amount.invalid}")
	private Double amount;
	private CustomerDTO customerDTO;
	private Integer rewardPoints;
}

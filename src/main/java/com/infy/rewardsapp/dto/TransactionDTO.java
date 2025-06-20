package com.infy.rewardsapp.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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

package com.infy.rewardsapp.dto;

import java.time.LocalDate;

public class TransactionDTO {
	private Integer transactionId;
	private LocalDate date;
	private Double amount;
	private CustomerDTO customerDto;
	private Integer rewardPoints;
}

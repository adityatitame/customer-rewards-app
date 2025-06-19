package com.infy.rewardsapp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionDTO {
	
	private Integer transactionId;
	private LocalDate date;
	private Double amount;
	private CustomerDTO customerDTO;
	private Integer rewardPoints;
}

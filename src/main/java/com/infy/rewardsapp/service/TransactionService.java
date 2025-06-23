package com.infy.rewardsapp.service;

import java.time.LocalDate;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.RewardSummary;
import com.infy.rewardsapp.model.TransactionDTO;

public interface TransactionService {
	
	public String addTransaction(TransactionDTO transaction) throws RewardsAppException;
	
	public RewardSummary calculateRewardPtsForTimeFrame(Integer customerId, LocalDate from, LocalDate to) throws RewardsAppException;
	
}

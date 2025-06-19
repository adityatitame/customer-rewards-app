package com.infy.rewardsapp.service;

import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.exception.RewardsAppException;

public interface TransactionService {
	
	public Integer addTransaction(TransactionDTO transaction) throws RewardsAppException;
	
}

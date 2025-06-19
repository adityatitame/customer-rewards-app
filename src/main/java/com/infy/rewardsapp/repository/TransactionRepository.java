package com.infy.rewardsapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	public List<TransactionDTO> findByCustomerCustomerId(Integer CustomerId);
	
}

package com.infy.rewardsapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.rewardsapp.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	public List<Transaction> findByCustomerCustomerId(Integer CustomerId);
	
	public List<Transaction> findByCustomerCustomerIdAndDateBetween(Integer customerId, LocalDate from, LocalDate to);
	
}

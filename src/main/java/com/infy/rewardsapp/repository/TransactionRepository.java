package com.infy.rewardsapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.rewardsapp.model.Transaction;

/**
 * Repository interface for accessing and managing {@link Transaction} entities.
 *
 * <p>Extends {@link CrudRepository} to provide standard CRUD operations.
 * Also defines custom query methods for retrieving transactions by customer and date range.
 */
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	/**
     * Retrieves all transactions for a given customer ID.
     *
     * @param customerId the ID of the customer
     * @return a list of transactions associated with the given customer
     */
	public List<Transaction> findByCustomerCustomerId(Integer CustomerId);
	
	/**
     * Retrieves transactions for a given customer that occurred within a specific date range.
     *
     * @param customerId the ID of the customer
     * @param from the start date (inclusive)
     * @param to the end date (inclusive)
     * @return a list of transactions for the customer within the specified date range
     */
	public List<Transaction> findByCustomerCustomerIdAndDateBetween(Integer customerId, LocalDate from, LocalDate to);
	
}

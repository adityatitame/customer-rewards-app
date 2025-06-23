package com.infy.rewardsapp.service;

import java.time.LocalDate;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.RewardSummary;
import com.infy.rewardsapp.model.TransactionDTO;

/**
 * Service interface for managing transactions and calculating reward points.
 * 
 * <p>Defines operations for adding customer transactions and generating
 * reward summaries based on transaction history within a given date range.
 */
public interface TransactionService {
	
	/**
     * Adds a new transaction for a customer and calculates reward points earned.
     *
     * @param transaction the transaction details including customer and amount
     * @return a confirmation message upon successful transaction recording
     * @throws RewardsAppException if transaction processing fails
     */
	public String addTransaction(TransactionDTO transaction) throws RewardsAppException;
	
	/**
     * Calculates the total and monthly reward points for a customer within a specific date range.
     *
     * @param customerId the ID of the customer whose rewards are to be calculated
     * @param from the start date of the calculation period
     * @param to the end date of the calculation period
     * @return a {@link RewardSummary} containing total and monthly reward points
     * @throws RewardsAppException if calculation fails due to invalid input or missing data
     */
	public RewardSummary calculateRewardPtsForTimeFrame(Integer customerId, LocalDate from, LocalDate to) throws RewardsAppException;
	
}

package com.infy.rewardsapp.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.Customer;
import com.infy.rewardsapp.model.CustomerDTO;
import com.infy.rewardsapp.model.MonthlyReward;
import com.infy.rewardsapp.model.RewardSummary;
import com.infy.rewardsapp.model.Transaction;
import com.infy.rewardsapp.model.TransactionDTO;
import com.infy.rewardsapp.repository.CustomerRepository;
import com.infy.rewardsapp.repository.TransactionRepository;

/**
 * Implementation of the {@link TransactionService} interface responsible for
 * handling transaction-related operations and reward point calculations.
 * 
 * <p>
 * This class handles adding transactions, computing reward points based on
 * transaction amount, and generating reward summaries for customers within a
 * specified date range.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Adds a new transaction for the given customer and calculates the reward
	 * points earned.
	 *
	 * @param transactionDTO the transaction details including amount, date, and
	 *                       customer ID
	 * @return a success message along with the reward points earned
	 * @throws RewardsAppException if the customer is not found in the database
	 */
	@Override
	public String addTransaction(TransactionDTO transactionDTO) throws RewardsAppException {

		Customer customer = customerRepository.findById(transactionDTO.getCustomerDTO().getCustomerId())
											  .orElseThrow(() -> new RewardsAppException("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND"));

		if (transactionDTO.getDate() != null && transactionDTO.getDate().isAfter(LocalDate.now())) {
			throw new RewardsAppException("TRANSACTIONSERVICE.INVALID_DATE");
		}

		int rewardPoints = calculateRewardPoints(transactionDTO.getAmount());

		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDate(transactionDTO.getDate() != null ? transactionDTO.getDate() : LocalDate.now());
		transaction.setCustomer(customer);
		transaction.setRewardPoints(rewardPoints);

		transactionRepository.save(transaction);

		customer.setTotalRewardPoints(customer.getTotalRewardPoints() + rewardPoints);
		customerRepository.save(customer);

		return "Transaction added successfully. Reward Points Earned: " + rewardPoints;
	}

	/**
	 * Calculates reward points based on transaction amount.
	 * 
	 * <ul>
	 * <li>No points for amount <= 50</li>
	 * <li>1 point per dollar for amount between 51–100</li>
	 * <li>2 points per dollar above 100, plus 50 points for the 51–100 range</li>
	 * </ul>
	 *
	 * @param amount the transaction amount
	 * @return reward points calculated for the amount
	 */
	private int calculateRewardPoints(double amount) {
		if (amount <= 50)
			return 0;
		if (amount <= 100)
			return (int) (amount - 50);
		return (int) ((amount - 100) * 2 + 50);
	}

	/**
	 * Calculates the total and monthly reward points earned by a customer within
	 * the specified date range.
	 *
	 * @param customerId the ID of the customer
	 * @param startDate  the beginning of the calculation period
	 * @param endDate    the end of the calculation period
	 * @return a {@link RewardSummary} containing customer info and earned points
	 * @throws RewardsAppException if the customer is not found or an error occurs
	 */
	@Override
	public RewardSummary calculateRewardPtsForTimeFrame(Integer customerId, LocalDate startDate, LocalDate endDate) throws RewardsAppException {

		if (startDate.isAfter(endDate)) {
			throw new RewardsAppException("TRANSACTIONSERVICE.INVALID_DATE_RANGE");
		}

		int totalPointsForRange = 0;

		Customer customer = customerRepository.findById(customerId)
											  .orElseThrow(() -> new RewardsAppException("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND" + customerId));

		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

		List<Transaction> transactions = transactionRepository.findByCustomerCustomerIdAndDateBetween(customerId, startDate, endDate);

		Map<YearMonth, Integer> monthlyPoints = new HashMap<>();

		transactions.forEach(tx -> {
			YearMonth ym = YearMonth.from(tx.getDate());
			int points = tx.getRewardPoints();

			monthlyPoints.put(ym, (monthlyPoints.containsKey(ym) ? monthlyPoints.get(ym) : 0) + points);
		});

		List<MonthlyReward> monthlyRewards = new ArrayList<MonthlyReward>();

		monthlyPoints.forEach((yearMonth, points) -> {
			MonthlyReward reward = new MonthlyReward();

			reward.setMonth(yearMonth.getMonth().toString());
			reward.setYear(yearMonth.getYear());
			reward.setRewardPoints(points);

			monthlyRewards.add(reward);
		});

		for (MonthlyReward reward : monthlyRewards) {
			totalPointsForRange += reward.getRewardPoints();
		}

		RewardSummary summary = new RewardSummary();
		summary.setCustomerId(customerDTO.getCustomerId());
		summary.setName(customerDTO.getName());
		summary.setContact(customerDTO.getContact());
		summary.setEmail(customerDTO.getEmail());
		summary.setTotalRewardPoints(customerDTO.getTotalRewardPoints());
		summary.setStartDate(startDate);
		summary.setEndDate(endDate);
		summary.setMonthlyRewards(monthlyRewards);
		summary.setTotalPointsForRange(totalPointsForRange);

		return summary;
	}

}

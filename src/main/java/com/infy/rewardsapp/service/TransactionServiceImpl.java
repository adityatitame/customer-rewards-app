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

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public String addTransaction(TransactionDTO transactionDTO) throws RewardsAppException {

		Customer customer = customerRepository.findById(transactionDTO.getCustomerDTO().getCustomerId())
				.orElseThrow(() -> new RewardsAppException("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND"));

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

	private int calculateRewardPoints(double amount) {
		if (amount <= 50)
			return 0;
		if (amount <= 100)
			return (int) (amount - 50);
		return (int) ((amount - 100) * 2 + 50);
	}

	@Override
	public RewardSummary calculateRewardPtsForTimeFrame(Integer customerId, LocalDate startDate, LocalDate endDate)
			throws RewardsAppException {
		int totalPointsForRange = 0;

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RewardsAppException("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND" + customerId));

		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		
		List<Transaction> transactions = transactionRepository.findByCustomerCustomerIdAndDateBetween(customerId, startDate,
				endDate);

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

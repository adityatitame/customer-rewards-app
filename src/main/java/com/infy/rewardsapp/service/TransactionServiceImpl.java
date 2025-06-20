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

import com.infy.rewardsapp.dto.CustomerDTO;
import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.entity.Customer;
import com.infy.rewardsapp.entity.Transaction;
import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.repository.CustomerRepository;
import com.infy.rewardsapp.repository.TransactionRepository;
import com.infy.rewardsapp.utility.MonthlyReward;
import com.infy.rewardsapp.utility.RewardSummary;

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
	public RewardSummary calculateRewardPtsForTimeFrame(Integer customerId, LocalDate from, LocalDate to)
			throws RewardsAppException {
		int totalPointsForRange = 0;

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RewardsAppException("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND" + customerId));

		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		
		List<Transaction> transactions = transactionRepository.findByCustomerCustomerIdAndDateBetween(customerId, from,
				to);

		Map<YearMonth, Integer> monthlyPoints = new HashMap<>();

		transactions.forEach(tx -> {
			YearMonth ym = YearMonth.from(tx.getDate());
			int points = tx.getRewardPoints();

			monthlyPoints.put(ym, (monthlyPoints.containsKey(ym) ? monthlyPoints.get(ym) : 0) + points);
		});

		List<MonthlyReward> monthlyRewards = new ArrayList<MonthlyReward>();

		monthlyPoints.forEach((yearMonth, points) -> {
			MonthlyReward reward = new MonthlyReward();

			String month = yearMonth.getMonth().toString();
			reward.setMonth(month + " " + yearMonth.getYear());
			reward.setRewardPoints(points);

			monthlyRewards.add(reward);
		});

		for (MonthlyReward reward : monthlyRewards) {
			totalPointsForRange += reward.getRewardPoints();
		}
		
		RewardSummary summary = new RewardSummary();
	    summary.setCustomerDTO(customerDTO);
		summary.setFrom(from);
		summary.setTo(to);
		summary.setMonthlyRewards(monthlyRewards);
		summary.setTotalPointsForRange(totalPointsForRange);
		
		return summary;
	}

}

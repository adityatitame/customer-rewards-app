package com.infy.rewardsapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.entity.Customer;
import com.infy.rewardsapp.entity.Transaction;
import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.repository.CustomerRepository;
import com.infy.rewardsapp.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
	@Override
	public String addTransaction(TransactionDTO transactionDTO) throws RewardsAppException {

		Customer customer = customerRepository.findById(transactionDTO.getCustomerDTO().getCustomerId())
	            .orElseThrow(() -> new RewardsAppException("Customer not found with ID: " + transactionDTO.getCustomerDTO().getCustomerId()));

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
	        if (amount <= 50) return 0;
	        if (amount <= 100) return (int) (amount - 50);
	        return (int) ((amount - 100) * 2 + 50);
	    }

}

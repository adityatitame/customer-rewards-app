package com.infy.rewardsapp.api;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.rewardsapp.dto.CustomerDTO;
import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.service.CustomerService;
import com.infy.rewardsapp.service.TransactionService;
import com.infy.rewardsapp.utility.RewardSummary;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/rewards")
public class RewardsAppAPI {
	
	@Autowired
    private TransactionService transactionService;
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/newTransaction")
	public ResponseEntity<String> newTransaction(@Valid @RequestBody TransactionDTO transactionDto) throws RewardsAppException{
		String response = transactionService.addTransaction(transactionDto);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws RewardsAppException{
		Integer id = customerService.addCustomer(customerDTO);
		return new ResponseEntity<String>("Customer Registered Successfully with Id: "+id ,HttpStatus.CREATED);
	}

	@GetMapping("/rewardSummary/{customerId}/{from}/{to}")
	public ResponseEntity<RewardSummary> getRewardSummary(@PathVariable Integer customerId, @PathVariable LocalDate from, @PathVariable LocalDate to) throws RewardsAppException
	{
		RewardSummary summary = transactionService.calculateRewardPtsForTimeFrame(customerId, from, to);
		return new ResponseEntity<RewardSummary>(summary, HttpStatus.OK);
	}
	
}

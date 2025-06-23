package com.infy.rewardsapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.CustomerDTO;
import com.infy.rewardsapp.model.RewardSummary;
import com.infy.rewardsapp.model.TransactionDTO;
import com.infy.rewardsapp.service.CustomerService;
import com.infy.rewardsapp.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/rewards")
public class RewardController {
	
	@Autowired
    private TransactionService transactionService;
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/addTransaction")
	public ResponseEntity<String> addTransaction(@Valid @RequestBody TransactionDTO transactionDto) throws RewardsAppException{
		String response = transactionService.addTransaction(transactionDto);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/addCustomer")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws RewardsAppException{
		Integer id = customerService.addCustomer(customerDTO);
		return new ResponseEntity<String>("Customer Registered Successfully with Id: "+id ,HttpStatus.CREATED);
	}

	@GetMapping("/rewardSummary")
	public ResponseEntity<RewardSummary> getRewardSummary(@RequestParam Integer customerId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws RewardsAppException
	{
		RewardSummary summary = transactionService.calculateRewardPtsForTimeFrame(customerId, startDate, endDate);
		return new ResponseEntity<RewardSummary>(summary, HttpStatus.OK);
	}
	
}

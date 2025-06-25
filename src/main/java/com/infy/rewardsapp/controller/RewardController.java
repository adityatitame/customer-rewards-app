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

/**
 * Controller class that exposes REST endpoints for managing customers and
 * transactions, and calculating reward points based on customer transactions.
 * 
 * <p>
 * This class handles requests to add customers, add transactions, and retrieve
 * a reward summary for a specific customer within a given date range.
 */
@RestController
@Validated
@RequestMapping(value = "/rewards")
public class RewardController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private CustomerService customerService;

	/**
	 * Adds a new transaction for a customer and calculates reward points.
	 *
	 * @param transactionDTO the transaction details including customer and amount
	 * @return a response entity with confirmation message and HTTP status 201
	 *         (Created)
	 * @throws RewardsAppException if transaction processing fails
	 */
	@PostMapping("/addTransaction")
	public ResponseEntity<String> addTransaction(@Valid @RequestBody TransactionDTO transactionDTO)
			throws RewardsAppException {
		String response = transactionService.addTransaction(transactionDTO);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

	/**
	 * Registers a new customer into the system.
	 *
	 * @param customerDTO the customer details to be added
	 * @return a response entity with success message and assigned customer ID
	 * @throws RewardsAppException if customer registration fails
	 */
	@PostMapping("/addCustomer")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws RewardsAppException {
		Integer id = customerService.addCustomer(customerDTO);
		return new ResponseEntity<String>("Customer Registered Successfully with Id: " + id, HttpStatus.CREATED);
	}

	/**
	 * Retrieves the reward summary for a given customer within a specific date
	 * range.
	 *
	 * @param customerId the ID of the customer whose rewards are to be retrieved
	 * @param startDate  the start date of the reward calculation period
	 * @param endDate    the end date of the reward calculation period
	 * @return the calculated reward summary including monthly breakdown and total
	 *         points
	 * @throws RewardsAppException if summary retrieval fails or inputs are invalid
	 */
	@GetMapping("/rewardSummary")
	public ResponseEntity<RewardSummary> getRewardSummary(@RequestParam Integer customerId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws RewardsAppException {
		RewardSummary summary = transactionService.calculateRewardPtsForTimeFrame(customerId, startDate, endDate);
		return new ResponseEntity<RewardSummary>(summary, HttpStatus.OK);
	}

}

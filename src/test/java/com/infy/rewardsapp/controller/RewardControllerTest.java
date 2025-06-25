package com.infy.rewardsapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.CustomerDTO;
import com.infy.rewardsapp.model.RewardSummary;
import com.infy.rewardsapp.model.TransactionDTO;
import com.infy.rewardsapp.service.CustomerService;
import com.infy.rewardsapp.service.TransactionService;

@SpringBootTest
public class RewardControllerTest {

	@Mock
	private TransactionService transactionService;

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private RewardController rewardController;

	@Test
	void testAddCustomer_Valid() throws RewardsAppException {
		CustomerDTO dto = new CustomerDTO();
		dto.setName("Test User");
		dto.setContact("9876543210");
		dto.setEmail("test@example.com");

		when(customerService.addCustomer(dto)).thenReturn(101);

		ResponseEntity<String> response = rewardController.addCustomer(dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Customer Registered Successfully with Id: 101", response.getBody());
	}

	@Test
	void testAddCustomer_Invalid() throws RewardsAppException {
		CustomerDTO dto = new CustomerDTO();
		dto.setName("Invalid");
		dto.setContact("1234567890");
		dto.setEmail("invalid@example.com");

		when(customerService.addCustomer(dto)).thenThrow(new RewardsAppException("CUSTOMERSERVICE.CUSTOMER_REGISTRATION_FAILED"));

		RewardsAppException exception = assertThrows(RewardsAppException.class,() -> rewardController.addCustomer(dto));

		assertEquals("CUSTOMERSERVICE.CUSTOMER_REGISTRATION_FAILED", exception.getMessage());
	}

	@Test
	void testAddTransaction_Valid() throws RewardsAppException {
		TransactionDTO dto = new TransactionDTO();
		dto.setAmount(120.0);

		when(transactionService.addTransaction(dto,1)).thenReturn("Transaction added successfully. Reward Points Earned: 90");

		ResponseEntity<String> response = rewardController.addTransaction(dto,1);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Transaction added successfully. Reward Points Earned: 90", response.getBody());
	}

	@Test
	void testAddTransaction_Invalid() throws RewardsAppException {
		TransactionDTO dto = new TransactionDTO();
		dto.setAmount(150.0);

		when(transactionService.addTransaction(dto,1)).thenThrow(new RewardsAppException("TRANSACTIONSERVICE.INVALID_CUSTOMER"));

		RewardsAppException exception = assertThrows(RewardsAppException.class,() -> rewardController.addTransaction(dto,1));

		assertEquals("TRANSACTIONSERVICE.INVALID_CUSTOMER", exception.getMessage());
	}

	@Test
	void testGetRewardSummary_Valid() throws RewardsAppException {
		Integer customerId = 1;
		LocalDate startDate = LocalDate.of(2024, 1, 1);
		LocalDate endDate = LocalDate.of(2024, 12, 31);

		RewardSummary mockSummary = new RewardSummary();
		mockSummary.setCustomerId(customerId);
		mockSummary.setTotalPointsForRange(150);

		when(transactionService.calculateRewardPtsForTimeFrame(customerId, startDate, endDate)).thenReturn(mockSummary);

		ResponseEntity<RewardSummary> response = rewardController.getRewardSummary(customerId, startDate, endDate);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockSummary, response.getBody());
	}

	@Test
	void testGetRewardSummary_Invalid() throws RewardsAppException {
		Integer customerId = 99;
		LocalDate from = LocalDate.of(2024, 1, 1);
		LocalDate to = LocalDate.of(2024, 12, 31);

		when(transactionService.calculateRewardPtsForTimeFrame(customerId, from, to)).thenThrow(new RewardsAppException("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND"));

		RewardsAppException exception = assertThrows(RewardsAppException.class,() -> rewardController.getRewardSummary(customerId, from, to));

		assertEquals("TRANSACTIONSERVICE.CUSTOMER_NOT_FOUND", exception.getMessage());
	}
}

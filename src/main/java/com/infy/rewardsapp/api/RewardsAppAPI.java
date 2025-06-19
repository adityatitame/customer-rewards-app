package com.infy.rewardsapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.exception.RewardsAppException;

@RestController
@RequestMapping(value = "/rewards")
public class RewardsAppAPI {
	
	
	public ResponseEntity<String> newTransaction(TransactionDTO transactionDto) throws RewardsAppException{
		String successMessage = "";
		
		return new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
	}
	
}

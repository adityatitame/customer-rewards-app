package com.infy.rewardsapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.rewardsapp.dto.CustomerDTO;
import com.infy.rewardsapp.dto.TransactionDTO;
import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.service.CustomerService;
import com.infy.rewardsapp.service.TransactionService;

@RestController
@RequestMapping(value = "/rewards")
public class RewardsAppAPI {
	
	@Autowired
    private TransactionService transactionService;
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/newTransaction")
	public ResponseEntity<String> newTransaction(@RequestBody TransactionDTO transactionDto) throws RewardsAppException{
		String response = transactionService.addTransaction(transactionDto);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) throws RewardsAppException{
		Integer id = customerService.addCustomer(customerDTO);
		return new ResponseEntity<String>("Customer Registered Successfully with Id: "+id ,HttpStatus.CREATED);
	}
	
	@GetMapping("/ping")
	public String ping() {
	    return "Service is up!";
	}

	
}

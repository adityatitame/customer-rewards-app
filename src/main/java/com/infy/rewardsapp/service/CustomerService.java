package com.infy.rewardsapp.service;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.CustomerDTO;

public interface CustomerService {
	
	public Integer addCustomer(CustomerDTO customerDTO) throws RewardsAppException;
	
}

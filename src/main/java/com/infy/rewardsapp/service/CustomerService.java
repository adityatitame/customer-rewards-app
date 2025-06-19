package com.infy.rewardsapp.service;

import com.infy.rewardsapp.dto.CustomerDTO;
import com.infy.rewardsapp.exception.RewardsAppException;

public interface CustomerService {
	
	public Integer addCustomer(CustomerDTO customerDTO) throws RewardsAppException;
	
}

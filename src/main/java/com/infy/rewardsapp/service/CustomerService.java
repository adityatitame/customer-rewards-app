package com.infy.rewardsapp.service;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.CustomerDTO;

/**
 * Service interface for managing customer-related operations in the rewards application.
 * 
 * <p>Defines the contract for adding new customers and handling related business logic.
 */
public interface CustomerService {
	
	/**
     * Registers a new customer and returns the generated customer ID.
     *
     * @param customerDTO the customer details to be saved
     * @return the ID of the newly registered customer
     * @throws RewardsAppException if customer registration fails
     */
	public Integer addCustomer(CustomerDTO customerDTO) throws RewardsAppException;
	
}

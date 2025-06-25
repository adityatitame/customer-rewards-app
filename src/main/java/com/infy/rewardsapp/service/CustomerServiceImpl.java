package com.infy.rewardsapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.Customer;
import com.infy.rewardsapp.model.CustomerDTO;
import com.infy.rewardsapp.repository.CustomerRepository;

/**
 * Implementation of the {@link CustomerService} interface that provides
 * functionality for adding customers to the rewards application.
 * 
 * <p>
 * This class handles the conversion of DTOs to entity models and persists
 * customer data using the {@link CustomerRepository}.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Adds a new customer to the system by saving their details to the database.
	 * Initializes the total reward points to 0.
	 *
	 * @param customerDTO the customer data transfer object containing registration
	 *                    details
	 * @return the generated customer ID after successful persistence
	 * @throws RewardsAppException if any error occurs during saving
	 */
	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws RewardsAppException {

		Customer customer = modelMapper.map(customerDTO, Customer.class);
		customer.setTotalRewardPoints(0);

		return customerRepository.save(customer).getCustomerId();
	}

}

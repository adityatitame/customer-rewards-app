package com.infy.rewardsapp.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.rewardsapp.dto.CustomerDTO;
import com.infy.rewardsapp.entity.Customer;
import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws RewardsAppException {
		
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		customer.setTotalRewardPoints(0);
		
		return customerRepository.save(customer).getCustomerId();
	}

}

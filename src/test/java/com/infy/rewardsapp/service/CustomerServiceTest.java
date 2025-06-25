package com.infy.rewardsapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.rewardsapp.exception.RewardsAppException;
import com.infy.rewardsapp.model.Customer;
import com.infy.rewardsapp.model.CustomerDTO;
import com.infy.rewardsapp.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	void testAddCustomer_Success() throws RewardsAppException {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName("Alice");
		customerDTO.setContact("9876543210");
		customerDTO.setEmail("alice@example.com");

		Customer customer = modelMapper.map(customerDTO, Customer.class);
		customer.setCustomerId(1);
		customer.setTotalRewardPoints(0);

		when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		Integer id = customerService.addCustomer(customerDTO);
		Assertions.assertEquals(1, id);
	}
}

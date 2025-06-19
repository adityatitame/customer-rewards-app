package com.infy.rewardsapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.infy.rewardsapp.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}

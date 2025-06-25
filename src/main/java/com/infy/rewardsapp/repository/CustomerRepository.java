package com.infy.rewardsapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.infy.rewardsapp.model.Customer;

/**
 * Repository interface for performing CRUD operations on {@link Customer}
 * entities.
 * 
 * <p>
 * Extends {@link CrudRepository} to provide basic database operations like
 * save, findById, delete, etc.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	// No custom methods required as of now — inherits basic CRUD functionality
}

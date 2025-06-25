package com.infy.rewardsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for starting the RewardsApp Spring Boot application.
 * 
 * <p>
 * This class contains the entry point (`main` method) which launches the Spring
 * application context and initializes all components.
 */
@SpringBootApplication
public class RewardsappApplication {

	/**
	 * Entry point of the RewardsApp application.
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(RewardsappApplication.class, args);
	}

}

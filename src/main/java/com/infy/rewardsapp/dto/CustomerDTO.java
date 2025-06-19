package com.infy.rewardsapp.dto;

import lombok.Data;

@Data
public class CustomerDTO {

	private Integer customerId;
	private String name;
	private String contact;
	private String email;
	private Integer rewardPoints;
}

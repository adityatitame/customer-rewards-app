package com.infy.rewardsapp.utility;

import java.time.LocalDate;
import java.util.List;

import com.infy.rewardsapp.dto.CustomerDTO;

import lombok.Data;

@Data
public class RewardSummary {
	private CustomerDTO customerDTO;
	private LocalDate from;
	private LocalDate to;
	private List<MonthlyReward> monthlyRewards;
	private Integer totalPointsForRange;
}

package com.infy.rewardsapp.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class RewardSummary {
	private Integer customerId;
	private String name;
	private String contact;
	private String email;
	private Integer totalRewardPoints;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<MonthlyReward> monthlyRewards;
	private Integer totalPointsForRange;
}

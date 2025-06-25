package com.infy.rewardsapp.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Represents a summary of reward points earned by a customer within a specific
 * date range.
 *
 * <p>
 * This class includes customer details, the total reward points accumulated,
 * the date range of the report, and a monthly breakdown of rewards.
 */
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

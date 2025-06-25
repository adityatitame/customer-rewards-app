package com.infy.rewardsapp.model;

import lombok.Data;

/**
 * Represents the reward points earned by a customer for a specific month and
 * year.
 *
 * <p>
 * This class is used as part of the
 * {@link com.infy.rewardsapp.model.RewardSummary} to provide a monthly
 * breakdown of reward points.
 */
@Data
public class MonthlyReward {

	private String month;
	private Integer year;
	private Integer rewardPoints;
}

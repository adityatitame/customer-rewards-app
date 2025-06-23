package com.infy.rewardsapp.model;

import lombok.Data;

/**
 * Represents the reward points earned by a customer for a specific month and year.
 *
 * <p>This class is used as part of the {@link com.infy.rewardsapp.model.RewardSummary}
 * to provide a monthly breakdown of reward points.
 */
@Data
public class MonthlyReward {
	
	/** The name of the month (e.g., "JANUARY", "FEBRUARY"). */
    private String month;
    
    /** The year for which the reward points are recorded. */
    private Integer year;
    
    /** Total reward points earned by the customer during the given month and year. */
    private Integer rewardPoints;
}


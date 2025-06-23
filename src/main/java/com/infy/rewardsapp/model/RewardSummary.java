package com.infy.rewardsapp.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Represents a summary of reward points earned by a customer within a specific date range.
 *
 * <p>This class includes customer details, the total reward points accumulated,
 * the date range of the report, and a monthly breakdown of rewards.
 */
@Data
public class RewardSummary {

    /** Unique identifier of the customer. */
    private Integer customerId;

    /** Full name of the customer. */
    private String name;

    /** Contact number of the customer. */
    private String contact;

    /** Email address of the customer. */
    private String email;

    /** Total reward points accumulated by the customer so far (lifetime). */
    private Integer totalRewardPoints;

    /** Start date of the reward summary period. */
    private LocalDate startDate;

    /** End date of the reward summary period. */
    private LocalDate endDate;

    /** List of reward points earned per month during the summary period. */
    private List<MonthlyReward> monthlyRewards;

    /** Total reward points earned within the specified date range. */
    private Integer totalPointsForRange;
}

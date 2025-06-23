package com.infy.rewardsapp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Entity representing a transaction made by a customer.
 *
 * <p>Each transaction records the amount spent, date of purchase,
 * and the reward points earned. It is linked to a {@link Customer}.
 */
@Data
@Entity
public class Transaction {

    /** Auto-generated unique identifier for the transaction. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    /** Date on which the transaction occurred. */
    private LocalDate date;

    /** Total amount spent in the transaction. */
    private Double amount;

    /** Reward points earned from this transaction. */
    private Integer rewardPoints;

    /** The customer who performed this transaction. */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

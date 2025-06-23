package com.infy.rewardsapp.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing transaction data exchanged between
 * client, controller, and service layers.
 *
 * <p>Includes basic transaction information, customer reference, and optional
 * reward points. Validation is applied on the amount field to ensure correctness.
 */
@Data
public class TransactionDTO {

    /** Unique identifier of the transaction (if available, typically for responses). */
    private Integer transactionId;

    /** Date of the transaction. If not provided, current date may be used by service logic. */
    private LocalDate date;

    /**
     * Amount spent in the transaction.
     * <p>Must be a non-negative value.
     */
    @NotNull(message = "{transaction.amount.absent}")
    @Min(value = 0, message = "{transaction.amount.invalid}")
    private Double amount;

    /** Customer associated with this transaction. */
    private CustomerDTO customerDTO;

    /** Reward points earned from this transaction (usually calculated internally). */
    private Integer rewardPoints;
}

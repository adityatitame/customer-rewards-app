package com.infy.rewardsapp.exception;

/**
 * Custom exception class for handling application-specific errors in the
 * RewardsApp.
 *
 * <p>
 * This exception is thrown when a business rule violation or
 * application-specific error occurs in the service layer or elsewhere in the
 * application logic.
 */
public class RewardsAppException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code RewardsAppException} with the specified error
	 * message.
	 *
	 * @param message the detail message that explains the reason for the exception
	 */
	public RewardsAppException(String message) {
		super(message);
	}

}

package com.infy.rewardsapp.exception;

import lombok.Data;

/**
 * Represents error details returned in the response body when an exception
 * occurs.
 * 
 * <p>
 * This class holds an error message and an associated error code that can be
 * used by clients to understand the nature of the failure.
 */
@Data
public class ErrorInfo {

	private String errorMessage;
	private Integer errorCode;

}

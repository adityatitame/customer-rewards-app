package com.infy.rewardsapp.exception;

import lombok.Data;

@Data
public class ErrorInfo {
	
	private String errorMessage;
	private Integer errorCode;

}

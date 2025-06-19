package com.infy.rewardsapp.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.infy.rewardsapp.exception.RewardsAppException;

@Aspect
@Component
public class LoggingAspect {
	private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);
	
	@AfterThrowing(pointcut = "execution(* com.infy.rewardsapp.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(RewardsAppException exception)
	{
		LOGGER.error(exception.getMessage(), exception);
	}
}

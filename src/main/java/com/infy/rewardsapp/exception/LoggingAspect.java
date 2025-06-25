package com.infy.rewardsapp.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging exceptions thrown by service layer implementations.
 * 
 * <p>
 * This aspect intercepts all methods in classes under the
 * <code>com.infy.rewardsapp.service</code> package ending with
 * <code>*Impl</code> and logs any {@link RewardsAppException} thrown by them.
 */
@Aspect
@Component
public class LoggingAspect {
	private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);

	/**
	 * Logs exceptions thrown by service layer methods.
	 *
	 * <p>
	 * This advice runs after any method in a service implementation throws a
	 * {@link RewardsAppException}. It logs the error message and stack trace using
	 * Log4j2.
	 *
	 * @param exception the exception thrown by the service method
	 */
	@AfterThrowing(pointcut = "execution(* com.infy.rewardsapp.service.*Impl.*(..))", throwing = "exception")
	public void logServiceException(RewardsAppException exception) {
		LOGGER.error(exception.getMessage(), exception);
	}
}

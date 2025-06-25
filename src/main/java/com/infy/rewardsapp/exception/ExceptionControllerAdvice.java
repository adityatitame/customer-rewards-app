package com.infy.rewardsapp.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;

/**
 * Global exception handler for the RewardsApp application.
 *
 * <p>
 * This class captures and handles application-specific, validation, and general
 * exceptions and returns structured error responses to the client using
 * {@link ErrorInfo}.
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

	private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);

	@Autowired
	private Environment environment;

	/**
	 * Handles all custom {@link RewardsAppException} exceptions.
	 *
	 * @param exception the thrown RewardsAppException
	 * @return a structured error response with a mapped message and HTTP 400 status
	 */
	@ExceptionHandler(RewardsAppException.class)
	public ResponseEntity<ErrorInfo> handleNgInsuranceException(RewardsAppException exception) {
		LOGGER.error(exception.getMessage(), exception);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(environment.getProperty(exception.getMessage(), "An unexpected error occurred."));

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles input validation-related exceptions such as:
	 * <ul>
	 * <li>{@link MethodArgumentNotValidException}</li>
	 * <li>{@link ConstraintViolationException}</li>
	 * </ul>
	 *
	 * @param exception the validation exception encountered
	 * @return a structured error response containing validation messages and HTTP
	 *         400 status
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> handleValidationExceptions(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);

		String errorMsg;

		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manvException = (MethodArgumentNotValidException) exception;
			errorMsg = manvException.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(", "));
		} else {
			ConstraintViolationException cvException = (ConstraintViolationException) exception;
			errorMsg = cvException.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.joining(", "));
		}

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage(errorMsg);

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles missing required request parameters in HTTP GET or POST requests.
	 *
	 * <p>
	 * This method catches {@link MissingServletRequestParameterException} when a
	 * required query or form parameter is not present in the request. It returns a
	 * descriptive error message indicating the missing parameter.
	 *
	 * @param ex the exception thrown when a required parameter is missing
	 * @return a {@link ResponseEntity} with an {@link ErrorInfo} object and HTTP
	 *         400 (Bad Request)
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorInfo> handleMissingParams(MissingServletRequestParameterException ex) {
		LOGGER.error("Missing request parameter", ex);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage("Missing required request parameter: " + ex.getParameterName());

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles type mismatch errors when request parameters cannot be converted to
	 * the expected type.
	 *
	 * <p>
	 * This method handles {@link MethodArgumentTypeMismatchException} which occurs
	 * when, for example, a string is passed instead of an integer or date. It
	 * returns a friendly message indicating which parameter was incorrectly
	 * formatted.
	 *
	 * @param ex the exception thrown due to parameter type mismatch
	 * @return a {@link ResponseEntity} with an {@link ErrorInfo} object and HTTP
	 *         400 (Bad Request)
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorInfo> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		LOGGER.error("Type mismatch error", ex);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setErrorMessage("Invalid input for parameter: " + ex.getName());

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles any unexpected/unhandled exceptions in the application.
	 *
	 * @param exception the generic exception encountered
	 * @return a structured error response with a default error message and HTTP 500
	 *         status
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> handleGeneralException(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfo.setErrorMessage(environment.getProperty("GENERAL_ERROR"));

		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

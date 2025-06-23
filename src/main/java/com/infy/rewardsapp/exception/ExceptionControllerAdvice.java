package com.infy.rewardsapp.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;

/**
 * Global exception handler for the RewardsApp application.
 *
 * <p>This class captures and handles application-specific, validation, and general exceptions
 * and returns structured error responses to the client using {@link ErrorInfo}.
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
     *     <li>{@link MethodArgumentNotValidException}</li>
     *     <li>{@link ConstraintViolationException}</li>
     * </ul>
     *
     * @param exception the validation exception encountered
     * @return a structured error response containing validation messages and HTTP 400 status
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> handleValidationExceptions(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        String errorMsg;

        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvException = (MethodArgumentNotValidException) exception;
            errorMsg = manvException.getBindingResult()
                                     .getAllErrors()
                                     .stream()
                                     .map(ObjectError::getDefaultMessage)
                                     .collect(Collectors.joining(", "));
        } else {
            ConstraintViolationException cvException = (ConstraintViolationException) exception;
            errorMsg = cvException.getConstraintViolations()
                                  .stream()
                                  .map(ConstraintViolation::getMessage)
                                  .collect(Collectors.joining(", "));
        }

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setErrorMessage(errorMsg);

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any unexpected/unhandled exceptions in the application.
     *
     * @param exception the generic exception encountered
     * @return a structured error response with a default error message and HTTP 500 status
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


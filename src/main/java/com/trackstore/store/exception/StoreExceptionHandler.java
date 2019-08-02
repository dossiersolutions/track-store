package com.trackstore.store.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StoreExceptionHandler extends ResponseEntityExceptionHandler {

  private static final int ERROR_CODE_NOT_FOUND = 1564660564;
  private static final int ERROR_CODE_SERVER_ERROR = 1564660571;

  @ExceptionHandler(StoreResourceNotFoundException.class)
  public final ResponseEntity<StoreExceptionErrorDetails> handleResourceNotFoundException(
      StoreResourceNotFoundException ex, WebRequest request) {
    StoreExceptionErrorDetails errorDetails = new StoreExceptionErrorDetails(
        ERROR_CODE_NOT_FOUND, new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(StoreConstantViolationException.class)
  public final ResponseEntity<StoreExceptionErrorDetails> handleConstraintViolationException(
      StoreConstantViolationException ex, WebRequest request) {
    StoreExceptionErrorDetails errorDetails = new StoreExceptionErrorDetails(
        ERROR_CODE_SERVER_ERROR, new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

package com.trackstore.store.exception.handler;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trackstore.store.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@JsonPropertyOrder({"code", "message", "details", "timestamp"})
class ErrorDetails {

  private String code = "404";
  private Date timestamp;
  private String message;
  private String details;

  ErrorDetails(String code, Date timestamp, String message, String details) {
    super();
    this.code = code;
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  ErrorDetails(Date timestamp, String message, String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  public String getCode() {
    return code;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }

}


@ControllerAdvice
public class ResourceNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

}

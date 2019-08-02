package com.trackstore.store.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "message", "details", "timestamp"})
public class StoreExceptionErrorDetails {

  private int code;
  private Date timestamp;
  private String message;
  private String details;

  StoreExceptionErrorDetails(int code, Date timestamp, String message, String details) {
    super();
    this.code = code;
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  public int getCode() {
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

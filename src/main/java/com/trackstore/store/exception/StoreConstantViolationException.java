package com.trackstore.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SEE_OTHER)
public class StoreConstantViolationException extends RuntimeException {

  static final long serialVersionUID = 2L;

  public StoreConstantViolationException(String message) {
    super(message);
  }

}

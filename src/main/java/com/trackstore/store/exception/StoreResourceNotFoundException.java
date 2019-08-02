package com.trackstore.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StoreResourceNotFoundException extends RuntimeException {

  static final long serialVersionUID = 3L;

  public StoreResourceNotFoundException(String message) {
    super(message);
  }

}

package com.niharika.payment.support;

import java.time.Instant;

public class ApiError {
  public Instant timestamp = Instant.now();
  public String error;
  public String message;
  public String path;
}

package com.niharika.payment.api.dto;

import com.niharika.payment.domain.PaymentStatus;
import java.time.Instant;

public class PaymentResponse {
  public Long id;
  public String idempotencyKey;
  public String merchantId;
  public String customerId;
  public long amountCents;
  public String currency;
  public PaymentStatus status;
  public String failureReason;
  public Instant createdAt;
  public Instant updatedAt;
}

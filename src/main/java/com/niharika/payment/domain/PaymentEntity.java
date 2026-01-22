package com.niharika.payment.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payments", indexes = {
    @Index(name="idx_payments_idempotency", columnList = "idempotencyKey", unique = true),
    @Index(name="idx_payments_status", columnList = "status")
})
public class PaymentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String idempotencyKey;

  @Column(nullable = false)
  private String merchantId;

  @Column(nullable = false)
  private String customerId;

  @Column(nullable = false)
  private long amountCents;

  @Column(nullable = false)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status = PaymentStatus.CREATED;

  private String failureReason;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  @Column(nullable = false)
  private Instant updatedAt = Instant.now();

  @PreUpdate
  void onUpdate() { this.updatedAt = Instant.now(); }

  public Long getId() { return id; }
  public String getIdempotencyKey() { return idempotencyKey; }
  public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
  public String getMerchantId() { return merchantId; }
  public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
  public String getCustomerId() { return customerId; }
  public void setCustomerId(String customerId) { this.customerId = customerId; }
  public long getAmountCents() { return amountCents; }
  public void setAmountCents(long amountCents) { this.amountCents = amountCents; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
  public PaymentStatus getStatus() { return status; }
  public void setStatus(PaymentStatus status) { this.status = status; }
  public String getFailureReason() { return failureReason; }
  public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
}

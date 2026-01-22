package com.niharika.payment.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePaymentRequest {

  @NotBlank
  private String merchantId;

  @NotBlank
  private String customerId;

  @NotNull
  @Min(1)
  private Long amountCents;

  @NotBlank
  private String currency;

  public String getMerchantId() { return merchantId; }
  public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
  public String getCustomerId() { return customerId; }
  public void setCustomerId(String customerId) { this.customerId = customerId; }
  public Long getAmountCents() { return amountCents; }
  public void setAmountCents(Long amountCents) { this.amountCents = amountCents; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
}

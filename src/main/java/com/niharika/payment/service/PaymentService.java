package com.niharika.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niharika.payment.api.dto.CreatePaymentRequest;
import com.niharika.payment.api.dto.PaymentResponse;
import com.niharika.payment.domain.PaymentEntity;
import com.niharika.payment.repo.PaymentRepository;
import com.niharika.payment.support.NotFoundException;
import com.niharika.payment.support.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

  private final PaymentRepository repo;
  private final PaymentProcessor processor;
  private final OutboxService outbox;
  private final WebhookClient webhook;
  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${app.payments.webhookUrl:http://localhost:8082/api/v1/webhooks/payment-status}")
  private String webhookUrl;

  public PaymentService(PaymentRepository repo, PaymentProcessor processor, OutboxService outbox, WebhookClient webhook) {
    this.repo = repo;
    this.processor = processor;
    this.outbox = outbox;
    this.webhook = webhook;
  }

  public PaymentResponse create(String idempotencyKey, CreatePaymentRequest req) {
    if (idempotencyKey == null || idempotencyKey.isBlank()) {
      throw new ValidationException("Missing Idempotency-Key header");
    }

    // Idempotency: return existing payment if same key repeated
    PaymentEntity existing = repo.findByIdempotencyKey(idempotencyKey).orElse(null);
    if (existing != null) {
      return toResponse(existing);
    }

    PaymentEntity p = new PaymentEntity();
    p.setIdempotencyKey(idempotencyKey);
    p.setMerchantId(req.getMerchantId());
    p.setCustomerId(req.getCustomerId());
    p.setAmountCents(req.getAmountCents());
    p.setCurrency(req.getCurrency());
    p = repo.save(p);

    // process payment
    p = processor.process(p);

    // outbox event
    try {
      String payload = mapper.writeValueAsString(Map.of(
          "paymentId", p.getId(),
          "status", p.getStatus(),
          "amountCents", p.getAmountCents(),
          "currency", p.getCurrency()
      ));
      outbox.add("PAYMENT_STATUS_CHANGED", "PAYMENT", p.getId(), payload);
    } catch (Exception ignored) {}

    // simulate webhook callback
    webhook.post(webhookUrl, Map.of("paymentId", p.getId(), "status", p.getStatus().toString()));

    return toResponse(p);
  }

  public PaymentResponse get(Long id) {
    PaymentEntity p = repo.findById(id).orElseThrow(() -> new NotFoundException("Payment not found: " + id));
    return toResponse(p);
  }

  public Page<PaymentResponse> list(Pageable pageable) {
    return repo.findAll(pageable).map(PaymentService::toResponse);
  }

  static PaymentResponse toResponse(PaymentEntity p) {
    PaymentResponse r = new PaymentResponse();
    r.id = p.getId();
    r.idempotencyKey = p.getIdempotencyKey();
    r.merchantId = p.getMerchantId();
    r.customerId = p.getCustomerId();
    r.amountCents = p.getAmountCents();
    r.currency = p.getCurrency();
    r.status = p.getStatus();
    r.failureReason = p.getFailureReason();
    r.createdAt = p.getCreatedAt();
    r.updatedAt = p.getUpdatedAt();
    return r;
  }
}

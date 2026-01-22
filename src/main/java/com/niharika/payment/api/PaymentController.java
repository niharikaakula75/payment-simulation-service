package com.niharika.payment.api;

import com.niharika.payment.api.dto.CreatePaymentRequest;
import com.niharika.payment.api.dto.PaymentResponse;
import com.niharika.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

  private final PaymentService service;

  public PaymentController(PaymentService service) {
    this.service = service;
  }

  @PostMapping
  public PaymentResponse create(
      @RequestHeader(name = "Idempotency-Key", required = false) String idempotencyKey,
      @Valid @RequestBody CreatePaymentRequest req) {
    return service.create(idempotencyKey, req);
  }

  @GetMapping("/{id}")
  public PaymentResponse get(@PathVariable Long id) {
    return service.get(id);
  }

  @GetMapping
  public Page<PaymentResponse> list(Pageable pageable) {
    return service.list(pageable);
  }
}

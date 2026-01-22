package com.niharika.payment.service;

import com.niharika.payment.domain.PaymentEntity;
import com.niharika.payment.domain.PaymentStatus;
import com.niharika.payment.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentProcessor {

  private final PaymentRepository repo;
  private final Random random = new Random();

  @Value("${app.payments.simulateDelayMs:350}")
  private long delayMs;

  @Value("${app.payments.successRatePct:85}")
  private int successRatePct;

  public PaymentProcessor(PaymentRepository repo) {
    this.repo = repo;
  }

  public PaymentEntity process(PaymentEntity p) {
    p.setStatus(PaymentStatus.PROCESSING);
    repo.save(p);

    try { Thread.sleep(delayMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

    boolean ok = random.nextInt(100) < successRatePct;
    if (ok) {
      p.setStatus(PaymentStatus.SUCCEEDED);
      p.setFailureReason(null);
    } else {
      p.setStatus(PaymentStatus.FAILED);
      p.setFailureReason("DECLINED_BY_BANK");
    }
    return repo.save(p);
  }
}

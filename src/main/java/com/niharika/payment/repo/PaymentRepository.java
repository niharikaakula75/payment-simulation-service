package com.niharika.payment.repo;

import com.niharika.payment.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
  Optional<PaymentEntity> findByIdempotencyKey(String idempotencyKey);
}

package com.niharika.payment.api;

import com.niharika.payment.domain.OutboxEventEntity;
import com.niharika.payment.repo.OutboxEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/outbox")
public class OutboxController {

  private final OutboxEventRepository repo;

  public OutboxController(OutboxEventRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public Page<OutboxEventEntity> list(Pageable pageable) {
    return repo.findAll(pageable);
  }
}

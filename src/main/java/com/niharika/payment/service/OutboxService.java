package com.niharika.payment.service;

import com.niharika.payment.domain.OutboxEventEntity;
import com.niharika.payment.repo.OutboxEventRepository;
import org.springframework.stereotype.Service;

@Service
public class OutboxService {

  private final OutboxEventRepository repo;

  public OutboxService(OutboxEventRepository repo) {
    this.repo = repo;
  }

  public void add(String eventType, String aggregateType, Long aggregateId, String payload) {
    OutboxEventEntity e = new OutboxEventEntity();
    e.setEventType(eventType);
    e.setAggregateType(aggregateType);
    e.setAggregateId(aggregateId);
    e.setPayload(payload);
    repo.save(e);
  }
}

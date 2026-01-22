package com.niharika.payment.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "outbox_events", indexes = {
    @Index(name="idx_outbox_created", columnList = "createdAt")
})
public class OutboxEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String eventType;

  @Column(nullable = false)
  private String aggregateType;

  @Column(nullable = false)
  private Long aggregateId;

  @Column(nullable = false, length = 4000)
  private String payload;

  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  public Long getId() { return id; }
  public String getEventType() { return eventType; }
  public void setEventType(String eventType) { this.eventType = eventType; }
  public String getAggregateType() { return aggregateType; }
  public void setAggregateType(String aggregateType) { this.aggregateType = aggregateType; }
  public Long getAggregateId() { return aggregateId; }
  public void setAggregateId(Long aggregateId) { this.aggregateId = aggregateId; }
  public String getPayload() { return payload; }
  public void setPayload(String payload) { this.payload = payload; }
  public Instant getCreatedAt() { return createdAt; }
}

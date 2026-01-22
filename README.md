# Payment Simulation Service (Spring Boot)

An enterprise-style payment microservice that demonstrates:
- idempotent payment processing
- payment state machine (CREATED -> PROCESSING -> SUCCEEDED/FAILED)
- outbox pattern for event publishing (stored in DB)
- webhook callback simulation

## Run
```bash
mvn spring-boot:run
```

Swagger UI: `http://localhost:8082/swagger-ui.html`

## Key Concepts
- **Idempotency-Key** header to prevent duplicate charges
- **Outbox events** persisted to DB to ensure reliable event emission
- **Webhook simulation** endpoint to represent downstream callback delivery

## Endpoints
- `POST /api/v1/payments` (create & process payment)
- `GET /api/v1/payments/{id}` (get payment)
- `GET /api/v1/payments` (list)
- `GET /api/v1/outbox` (view events emitted)

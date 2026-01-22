package com.niharika.payment.api;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhooks")
public class WebhookController {

  // This simulates a downstream system receiving a webhook callback
  @PostMapping("/payment-status")
  public void paymentStatus(@RequestBody Map<String, Object> payload) {
    System.out.println("[WEBHOOK RECEIVED] " + payload);
  }
}

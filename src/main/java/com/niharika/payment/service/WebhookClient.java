package com.niharika.payment.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebhookClient {

  private final RestTemplate restTemplate = new RestTemplate();

  public void post(String url, Object payload) {
    restTemplate.postForEntity(url, payload, Void.class);
  }
}

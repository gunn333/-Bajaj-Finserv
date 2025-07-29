package com.example.webhookclient.service;

import com.example.webhookclient.config.WebhookProperties;
import com.example.webhookclient.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RegistrationService {

  private final RestTemplate restTemplate = new RestTemplate();
  private final WebhookProperties props;
  private final Logger logger =
      LoggerFactory.getLogger(RegistrationService.class);

  public WebhookResponse register() {
    String url =
        "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    RegistrationRequest req = new RegistrationRequest(
        props.getName(), props.getRegNo(), props.getEmail());

    logger.info("Registering webhook for {}", props.getName());
    ResponseEntity<WebhookResponse> response =
        restTemplate.postForEntity(url, req, WebhookResponse.class);

    if (response.getStatusCode() == HttpStatus.OK &&
        response.getBody() != null) {
      logger.info("Webhook received: {}", response.getBody().getWebhook());
      return response.getBody();
    } else {
      throw new RuntimeException("Webhook registration failed: " +
                                 response.getStatusCode());
    }
  }
}

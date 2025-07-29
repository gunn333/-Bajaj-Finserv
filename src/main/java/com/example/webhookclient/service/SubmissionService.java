package com.example.webhookclient.service;

import com.example.webhookclient.config.WebhookProperties;
import com.example.webhookclient.model.QuerySubmission;
import com.example.webhookclient.model.WebhookResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SubmissionService {

  private final RegistrationService registrationService;
  private final WebhookProperties props;
  private final RestTemplate restTemplate = new RestTemplate();
  private final Logger logger =
      LoggerFactory.getLogger(SubmissionService.class);

  @PostConstruct
  public void runOnStartup() {
    try {
      WebhookResponse response = registrationService.register();
      String token = response.getAccessToken();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(token);

      QuerySubmission submission = new QuerySubmission(props.getFinalQuery());
      HttpEntity<QuerySubmission> entity =
          new HttpEntity<>(submission, headers);

      logger.info("Submitting final SQL query...");
      ResponseEntity<String> submitResponse = restTemplate.exchange(
          "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA",
          HttpMethod.POST, entity, String.class);

      logger.info("Submission response: {}", submitResponse.getBody());
    } catch (Exception e) {
      logger.error("Error occurred during submission", e);
    }
  }
}

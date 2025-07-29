package com.example.webhookclient.service;

import com.example.webhookclient.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

  private final RestTemplate restTemplate = new RestTemplate();

  @PostConstruct
  public void onStartup() {
    try {
      String registrationUrl =
          "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

      RegistrationRequest request =
          new RegistrationRequest("John Doe", "REG12347", "john@example.com");

      ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
          registrationUrl, request, WebhookResponse.class);

      if (response.getStatusCode() == HttpStatus.OK &&
          response.getBody() != null) {
        String webhook = response.getBody().getWebhook();
        String token = response.getBody().getAccessToken();

        System.out.println("Webhook URL: " + webhook);
        System.out.println("Access Token: " + token);

        String finalQuery = "SELECT name FROM students WHERE marks > 75";

        String submitUrl =
            "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        QuerySubmission submission = new QuerySubmission(finalQuery);
        HttpEntity<QuerySubmission> entity =
            new HttpEntity<>(submission, headers);

        ResponseEntity<String> submitResponse = restTemplate.exchange(
            submitUrl, HttpMethod.POST, entity, String.class);

        System.out.println("Submission response: " + submitResponse.getBody());

      } else {
        System.out.println("Error in webhook registration.");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

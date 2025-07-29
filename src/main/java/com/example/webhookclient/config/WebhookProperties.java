package com.example.webhookclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "webhook")
public class WebhookProperties {
  private String name;
  private String regNo;
  private String email;
  private String finalQuery;
}

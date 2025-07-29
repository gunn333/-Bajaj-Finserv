package com.example.webhookclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {
  private String name;
  private String regNo;
  private String email;
}

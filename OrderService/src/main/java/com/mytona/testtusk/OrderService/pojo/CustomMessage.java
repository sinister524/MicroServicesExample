package com.mytona.testtusk.OrderService.pojo;

import lombok.Data;

@Data
public class CustomMessage {

  private String message;

  public CustomMessage(final String message) {
    this.message = message;
  }
}

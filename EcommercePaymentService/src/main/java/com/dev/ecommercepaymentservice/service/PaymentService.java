package com.dev.ecommercepaymentservice.service;


import com.dev.ecommercepaymentservice.dto.User;

public interface PaymentService {
  public String createLink(String orderId, User user) throws Exception;
    public String getStatus(String orderId);
}

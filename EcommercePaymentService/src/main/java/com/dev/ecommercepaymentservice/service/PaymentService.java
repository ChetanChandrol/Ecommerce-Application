package com.dev.ecommercepaymentservice.service;

public interface PaymentService {
  public   String createLink(String orderId) throws Exception;
    public String getStatus(String orderId);
}

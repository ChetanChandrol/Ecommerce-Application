package com.dev.ecommercepaymentservice.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService{
    @Override
    public String createLink(String orderId) throws Exception {
        return "";
    }

    @Override
    public String getStatus(String orderId) {
        return "";
    }
}

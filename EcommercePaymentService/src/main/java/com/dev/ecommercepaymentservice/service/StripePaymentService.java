package com.dev.ecommercepaymentservice.service;

import com.dev.ecommercepaymentservice.dto.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService{
    @Override
    public String createLink(String orderId, User user) throws Exception {
        return "";
    }

    @Override
    public String getStatus(String orderId) {
        return "";
    }
}

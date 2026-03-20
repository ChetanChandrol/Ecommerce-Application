package com.dev.ecommercepaymentservice.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfiguration {

    @Value("${razorpay.key.id}")
    private String razorPayKeyId;
    @Value("${razorpay.key.secret}")
    private String getRazorPayKeySecret;

    @Bean
    public RazorpayClient createRazorPayClient() throws RazorpayException {
        return new RazorpayClient(razorPayKeyId, getRazorPayKeySecret);
    }
}
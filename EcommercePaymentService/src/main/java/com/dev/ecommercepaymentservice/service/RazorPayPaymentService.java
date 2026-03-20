package com.dev.ecommercepaymentservice.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RazorPayPaymentService implements PaymentService {
    private RazorpayClient razorpayClient;

    @Autowired
    public RazorPayPaymentService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String createLink(String orderId) throws Exception {
        JSONObject paymentLinkRequest = new JSONObject();
        // Computers are not good with decimals
        // computers dont store the exact value of a decimal.
        // computers stores approximations for decimals.
        // Real amount = 10.01
        // Real amount = 99.99
        // Real amount = 150.33
        // give the value as RealAmount * 100.
        paymentLinkRequest.put("amount", 1001);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
//        paymentLinkRequest.put("first_min_partial_amount",500);
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + (15 * 60 * 1000));
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Payment for Order Id " + orderId);

        JSONObject customer = new JSONObject();
        customer.put("name","Chetan Chandrol");
        customer.put("contact","+917869027703");
        customer.put("email","chandrolchetan@gmail.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("payment for Iphone","IPhone 15");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://google.com");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.get("short_url");
    }

    @Override
    public String getStatus(String orderId) {
        return "";
    }
}

package com.dev.ecommercepaymentservice.service;


import com.dev.ecommercepaymentservice.dto.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RazorPayPaymentService implements PaymentService {
    private RazorpayClient  razorpayClient;

    @Autowired
    public RazorPayPaymentService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String createLink(String orderId, User user) throws Exception {
        JSONObject paymentLinkRequest = new JSONObject();
        System.out.println("AMOUNTTT"+user.getAmount());
        paymentLinkRequest.put("amount",user.getAmount()*100 );
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
//        paymentLinkRequest.put("first_min_partial_amount",500);
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + (15 * 60 * 1000));
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Payment for Order Id " + orderId);

        JSONObject customer = new JSONObject();
        customer.put("name", user.getUserName());
        customer.put("contact",user.getMobileNumber());
        customer.put("email","chetan@gmail.com");
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

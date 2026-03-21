package com.dev.ecommercepaymentservice.controller;

import com.dev.ecommercepaymentservice.dto.User;
import com.dev.ecommercepaymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}")
    public String createPaymentLink(@PathVariable String orderId, @RequestBody User user) throws Exception {
        return paymentService.createLink(orderId, user);
    }

    @GetMapping
    public String getStatus(@PathVariable("orderId") String orderId) {
        return null;
    }

    @PostMapping("/webhook")
    public void handleWebhookEvent(@RequestBody Object webHookEvent) {
        System.out.println("Handle web hook");
    }


}

package com.dev.EmailService.consumer;

import com.dev.EmailService.dto.SignupEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SignUpEventConsumer {

    private final ObjectMapper objectMapper;
    private final JavaMailSender mailSender; // 1. Inject the Mail Sender

    public SignUpEventConsumer(ObjectMapper objectMapper, JavaMailSender mailSender) {
        this.objectMapper = objectMapper;
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "signupEventTopic",
            groupId = "signupEventConsumerGroup")
    public void handleSignUpEvent(String message) {
        try {

            SignupEventDto signupEventDto = objectMapper.readValue(message, SignupEventDto.class);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(signupEventDto.getTo());
            mailMessage.setFrom(signupEventDto.getFrom());
            mailMessage.setSubject(signupEventDto.getSubject());
            mailMessage.setText(signupEventDto.getBody());

            mailSender.send(mailMessage);

            System.out.println("Email sent successfully to " + signupEventDto.getTo());

        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
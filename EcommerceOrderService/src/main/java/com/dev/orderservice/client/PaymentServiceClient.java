package com.dev.orderservice.client;


import com.dev.orderservice.dto.ApiResponse;
import com.dev.orderservice.dto.User;
import com.dev.orderservice.exception.OrderServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentServiceClient {
    private RestTemplateBuilder restTemplateBuilder;
    @Value("${paymentService.base.url}")
    private String paymentServiceBaseUrl;
    @Value("${paymentService.validate.api.url}")
    private String createPaymentLinkApiUrl;


    public PaymentServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public String createPaymentLink(Long orderId, User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("INSIDE createPaymentLink TOKEN");

            String url = paymentServiceBaseUrl + createPaymentLinkApiUrl;

            RestTemplate restTemplate = restTemplateBuilder.build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            requestEntity,
                            String.class, orderId
                    );
            System.out.println("response.getBody()");
            return response.getBody();
        } catch (HttpStatusCodeException errorException) {
            HttpStatus status = HttpStatus.resolve(errorException.getStatusCode().value());

            ApiResponse<String> apiResponse = mapper.readValue(errorException.getResponseBodyAsString(),
                    new TypeReference<ApiResponse<String>>() {});

            throw new OrderServiceException(apiResponse.getResponse(),status);
        }

    }
}

package com.dev.orderservice.client;



import com.dev.orderservice.dto.ValidateTokenResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {
    private  RestTemplateBuilder restTemplateBuilder;
    @Value("${userservice.api.url}")
    private String userServiceUrl;
    @Value("${userservice.api.path.validate}")
    private String userServiceValidatePath;


    public UserServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public ValidateTokenResponseDto validateToken(String token) throws JsonProcessingException {
        String response = null;
        try {
            String validateTokenUrl = userServiceUrl + userServiceValidatePath;
            RestTemplate restTemplate = restTemplateBuilder.build();

            System.out.println(token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);

            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<String> validateTokenResponse = restTemplate.exchange(validateTokenUrl,
                    HttpMethod.GET,
                    httpEntity,
                    String.class);

            response = validateTokenResponse.getBody();
        } catch (HttpClientErrorException ex) {
            response = ex.getResponseBodyAsString();
        }
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        ValidateTokenResponseDto validateTokenResponseDto =
                objectMapper.readValue(jsonNode.get("response").toString(), ValidateTokenResponseDto.class);

        return validateTokenResponseDto;
    }
}

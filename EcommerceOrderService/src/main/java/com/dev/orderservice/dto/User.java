package com.dev.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String userName;
    private String userId;
    private String mobileNumber;
    private String email;
    private Double amount;
}

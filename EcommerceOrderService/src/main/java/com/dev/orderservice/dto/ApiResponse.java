package com.dev.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private T response;
    private String status;
}

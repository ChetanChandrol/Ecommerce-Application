package com.dev.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddToCartRequestDTO {
    private UUID productId;
    private Integer quantity;
}

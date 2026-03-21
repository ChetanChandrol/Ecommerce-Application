package com.dev.orderservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderItem extends BaseModel {

    private UUID productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
package com.dev.orderservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Entity
public class CartItem extends BaseModel {
    private UUID productId;
    private Integer quantity;
}

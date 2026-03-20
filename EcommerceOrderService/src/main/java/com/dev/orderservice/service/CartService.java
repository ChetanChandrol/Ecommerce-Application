package com.dev.orderservice.service;

import com.dev.orderservice.dto.AddToCartRequestDTO;
import com.dev.orderservice.models.Cart;

import java.util.UUID;

public interface CartService {
    Cart addToCart(UUID userId, AddToCartRequestDTO request);
    Cart getCart(UUID userId);
    void updateQuantity(UUID userId, Long productId, Integer quantity);
    void deleteItem(UUID userId, Long productId);
}

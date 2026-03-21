package com.dev.orderservice.service;

import com.dev.orderservice.dto.OrderRequestDto;
import com.dev.orderservice.models.Order;
import com.dev.orderservice.security.LoggedUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    public HashMap<String,Object> createOrder(LoggedUser user, OrderRequestDto orderRequestDto) throws JsonProcessingException;
    public List<Order> getAllOrders(UUID userId);
}

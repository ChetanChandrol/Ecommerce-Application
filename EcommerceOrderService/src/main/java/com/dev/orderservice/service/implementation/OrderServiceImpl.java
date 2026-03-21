package com.dev.orderservice.service.implementation;

import com.dev.orderservice.client.PaymentServiceClient;
import com.dev.orderservice.dto.OrderRequestDto;
import com.dev.orderservice.dto.User;
import com.dev.orderservice.enums.OrderStatus;
import com.dev.orderservice.models.Order;
import com.dev.orderservice.models.OrderItem;
import com.dev.orderservice.repository.OrderRepository;
import com.dev.orderservice.security.LoggedUser;
import com.dev.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Override
    @Transactional
    public HashMap<String, Object> createOrder(LoggedUser loggedUser, OrderRequestDto orderRequestDto) throws JsonProcessingException {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.addAll(orderRequestDto.getOrderItemList());

        Order order = new Order();
        order.setOrderItems(orderItemList);
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        order.setTotalAmount(orderRequestDto.getOrderAmount());
        order.setUserId(loggedUser.getId());
        Order finalOrder = orderRepository.save(order);

        User user = new User();
        user.setUserId(loggedUser.getId().toString());
        user.setAmount(orderRequestDto.getOrderAmount());
        user.setEmail(loggedUser.getEmail());

        String paymentLink = paymentServiceClient.createPaymentLink(order.getId(), user);

        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", finalOrder);
        responseMap.put("link", paymentLink);

        return responseMap;
    }

    @Override
    public List<Order> getAllOrders(UUID userId) {
        return orderRepository.findAllByUserId(userId);
    }


}

package com.dev.orderservice.controller;

import com.dev.orderservice.dto.OrderRequestDto;
import com.dev.orderservice.models.Order;
import com.dev.orderservice.security.LoggedUser;
import com.dev.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.dev.orderservice.utils.OrderServiceUtil.buildResponse;
import static com.dev.orderservice.utils.SecurityUtils.getUser;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<HashMap<String, Object>> placeOrder(@RequestBody OrderRequestDto orderRequestDto) throws JsonProcessingException {
        LoggedUser loggedUser = getUser();
        assert loggedUser != null;
        HashMap<String,Object> response = orderService.createOrder(loggedUser, orderRequestDto);
        return buildResponse(response,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getALlOrders() {
        LoggedUser loggedUser = getUser();
        assert loggedUser != null;
        return buildResponse(orderService.getAllOrders(loggedUser.getId()),HttpStatus.OK);
    }


}
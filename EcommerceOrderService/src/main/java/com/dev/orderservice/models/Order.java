package com.dev.orderservice.models;

import com.dev.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseModel {

    private UUID userId;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String paymentId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;
}
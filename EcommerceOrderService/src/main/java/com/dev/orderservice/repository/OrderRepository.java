package com.dev.orderservice.repository;

import com.dev.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserId(UUID userId);
}

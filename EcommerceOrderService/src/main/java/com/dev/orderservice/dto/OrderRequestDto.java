package com.dev.orderservice.dto;

import com.dev.orderservice.models.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    List<OrderItem> orderItemList;
    private double orderAmount;
}

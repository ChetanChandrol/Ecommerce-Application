package com.dev.orderservice.controller;

import com.dev.orderservice.dto.AddToCartRequestDTO;
import com.dev.orderservice.models.Cart;
import com.dev.orderservice.security.LoggedUser;
import com.dev.orderservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.dev.orderservice.utils.OrderServiceUtil.buildResponse;
import static com.dev.orderservice.utils.SecurityUtils.getUser;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addToCart(@RequestBody AddToCartRequestDTO request) {
        LoggedUser loggedUser= getUser();
        assert loggedUser != null;
        return buildResponse(cartService.addToCart(loggedUser.getId(),request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getCart() {
        LoggedUser loggedUser= getUser();
        assert loggedUser != null;
        return buildResponse(cartService.getCart(loggedUser.getId()), HttpStatus.OK);
    }

    @PutMapping("/update/{productId}/{quantity}")
    public void updateQuantity(@PathVariable Long productId,@PathVariable Integer quantity)
    {   LoggedUser loggedUser= getUser();
        assert loggedUser != null;
        cartService.updateQuantity(loggedUser.getId(),productId,quantity);
    }
    @DeleteMapping("/delete/{userID}/{productId}")
    public void deleteItem(@PathVariable Long productId)
    {   LoggedUser loggedUser= getUser();
        assert loggedUser != null;
        cartService.deleteItem(loggedUser.getId(),productId);
    }
}
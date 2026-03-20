package com.dev.orderservice.controller;

import com.dev.orderservice.dto.AddToCartRequestDTO;
import com.dev.orderservice.models.Cart;
import com.dev.orderservice.security.LoggedUser;
import com.dev.orderservice.service.CartService;
import org.springframework.web.bind.annotation.*;

import static com.dev.orderservice.utils.SecurityUtils.getUser;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody AddToCartRequestDTO request) {
        LoggedUser loggedUser= getUser();
        assert loggedUser != null;
        return cartService.addToCart(loggedUser.getId(),request);
    }

    @GetMapping
    public Cart getCart() {
        LoggedUser loggedUser= getUser();
        assert loggedUser != null;
        return cartService.getCart(loggedUser.getId());
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
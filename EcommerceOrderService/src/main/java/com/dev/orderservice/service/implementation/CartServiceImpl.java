package com.dev.orderservice.service.implementation;

import com.dev.orderservice.dto.AddToCartRequestDTO;
import com.dev.orderservice.exception.OrderServiceException;
import com.dev.orderservice.models.Cart;
import com.dev.orderservice.models.CartItem;
import com.dev.orderservice.repository.CartRepository;
import com.dev.orderservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart addToCart(UUID userId, AddToCartRequestDTO request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setCartItems(new ArrayList<>());
                    return newCart;
                });

        List<CartItem> cartItems = cart.getCartItems();

        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());

        } else {

            CartItem newItem = new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());

            cartItems.add(newItem);
        }

        cart.setCartItems(cartItems);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(UUID userId) {

        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new OrderServiceException("Cart Not Found", HttpStatus.BAD_REQUEST));
    }

    public void updateQuantity(UUID userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderServiceException("Cart not found",HttpStatus.BAD_REQUEST));

        cart.getCartItems().removeIf(item -> {
            if (item.getProductId().equals(productId)) {
                if (quantity <= 0) return true;
                item.setQuantity(quantity);
            }
            return false;
        });

        cartRepository.save(cart);
    }
    public void deleteItem(UUID userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderServiceException("Cart not found",HttpStatus.BAD_REQUEST));
        cart.getCartItems().removeIf(cartItem -> cartItem.getProductId().equals(productId));
        cartRepository.save(cart);
    }

    
}


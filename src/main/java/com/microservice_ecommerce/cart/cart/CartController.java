package com.microservice_ecommerce.cart.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart")
public class CartController {

    protected CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> index() {
        Long userId = 1L;

        CartResponse CartResponse = cartService.findByUserId(userId);

        return ResponseEntity.ok(CartResponse);
    }

}

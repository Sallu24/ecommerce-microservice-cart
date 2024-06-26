package com.microservice_ecommerce.cart.cart.internal;

import com.microservice_ecommerce.cart.cart.CartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cart/internal")
public class CartInternalController {

    protected CartInternalService cartService;

    public CartInternalController(CartInternalService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getUserCart() {
        Long userId = 1L;

        CartResponse CartResponse = cartService.findByUserId(userId);

        return ResponseEntity.ok(CartResponse);
    }

}

package com.microservice_ecommerce.cart.cart.internal;

import com.microservice_ecommerce.cart.cart.*;
import com.microservice_ecommerce.cart.cart.external.Product;
import com.microservice_ecommerce.cart.cart.mapper.CartItemMapper;
import com.microservice_ecommerce.cart.cart.mapper.CartMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartInternalService {

    protected CartRepository cartRepository;

    protected CartItemRepository cartItemRepository;

    protected RestTemplate restTemplate;

    public CartInternalService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            RestTemplate restTemplate
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.restTemplate = restTemplate;
    }

    protected CartResponse findByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return convertToDTO(cart);
    }

    private CartResponse convertToDTO(Cart cart) {
        if (cart == null) {
            return new CartResponse();
        }

        List<CartItemResponse> cartItemResponses = null;
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems != null && !cartItems.isEmpty()) {
            cartItemResponses = cartItems.stream()
                    .map(cartItem -> {
                        Product product = restTemplate.getForObject(
                                "http://PRODUCT:8093/api/products/" + cartItem.getProductId(),
                                Product.class
                        );

                        return CartItemMapper.cartItemResponse(cartItem, product);
                    })

                    .toList();
        }

        return CartMapper.cartResponse(cart, cartItemResponses);
    }
}

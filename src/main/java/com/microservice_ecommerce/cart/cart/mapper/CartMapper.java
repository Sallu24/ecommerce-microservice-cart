package com.microservice_ecommerce.cart.cart.mapper;

import com.microservice_ecommerce.cart.cart.Cart;
import com.microservice_ecommerce.cart.cart.CartItemResponse;
import com.microservice_ecommerce.cart.cart.CartResponse;

import java.util.List;

public class CartMapper {

    public static CartResponse cartResponse(Cart cart, List<CartItemResponse> cartItemResponse) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setUserId(cart.getUserId());
        cartResponse.setTotal(cart.getTotal());
        cartResponse.setCartItems(cartItemResponse);
        cartResponse.setCreatedAt(cart.getCreatedAt());
        cartResponse.setUpdatedAt(cart.getUpdatedAt());

        return cartResponse;
    }

}

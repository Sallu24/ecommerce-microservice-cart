package com.microservice_ecommerce.cart.cart.mapper;

import com.microservice_ecommerce.cart.cart.CartItem;
import com.microservice_ecommerce.cart.cart.CartItemResponse;
import com.microservice_ecommerce.cart.cart.external.Product;

public class CartItemMapper {

    public static CartItemResponse cartItemResponse(CartItem cartItem, Product product) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setId(cartItem.getId());
        cartItemResponse.setProduct(product);
        cartItemResponse.setQty(cartItem.getQty());
        cartItemResponse.setPrice(cartItem.getPrice());
        cartItemResponse.setCreatedAt(cartItem.getCreatedAt());
        cartItemResponse.setUpdatedAt(cartItem.getUpdatedAt());

        return cartItemResponse;
    }

}

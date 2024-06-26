package com.microservice_ecommerce.cart.cart;

public class CartItemAlreadyExistsException extends RuntimeException {

    public CartItemAlreadyExistsException(String message) {
        super(message);
    }

}

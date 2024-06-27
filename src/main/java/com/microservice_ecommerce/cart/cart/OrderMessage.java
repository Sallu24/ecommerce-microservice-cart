package com.microservice_ecommerce.cart.cart;

public class OrderMessage {

    private Long id;

    private Long userId;

    private Long cartId;

    public OrderMessage(Long id, Long userId, Long cartId) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "id=" + id +
                ", userId=" + userId +
                ", cartId=" + cartId +
                '}';
    }

}

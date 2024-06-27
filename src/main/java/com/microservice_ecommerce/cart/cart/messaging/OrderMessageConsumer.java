package com.microservice_ecommerce.cart.cart.messaging;

import com.microservice_ecommerce.cart.cart.CartService;
import com.microservice_ecommerce.cart.cart.OrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageConsumer {

    private final CartService cartService;

    public OrderMessageConsumer(CartService cartService) {
        this.cartService = cartService;
    }

    @RabbitListener(queues = "cartEmptyQueue")
    public void consumeMessage(OrderMessage orderMessage) {
        cartService.emptyCart(orderMessage);
    }

}

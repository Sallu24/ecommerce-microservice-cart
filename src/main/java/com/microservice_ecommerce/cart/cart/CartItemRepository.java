package com.microservice_ecommerce.cart.cart;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    void deleteByCartId(Long cartId);

}

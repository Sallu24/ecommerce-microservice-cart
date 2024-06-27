package com.microservice_ecommerce.cart.cart;

import com.microservice_ecommerce.cart.cart.external.Product;
import com.microservice_ecommerce.cart.cart.mapper.CartItemMapper;
import com.microservice_ecommerce.cart.cart.mapper.CartMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    protected CartRepository cartRepository;

    protected CartItemRepository cartItemRepository;

    protected RestTemplate restTemplate;

    public CartService(
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

    protected void addItem(CartItemCreationDTO cartItemCreationDTO) {
        Long userId = 1L;
//        User user = userService.findById(userId);

//        Cart userCart = cartRepository.findByUserId(user.getId());
        Cart userCart = cartRepository.findByUserId(userId);
        if (userCart == null) {
            // create a new cart
            Cart cart = new Cart();
            cart.setUserId(userId);

            userCart = cartRepository.save(cart);
        }

        addItemToCart(cartItemCreationDTO, userCart);
    }

    protected void updateItem(Long cartItemId, CartItemUpdateDTO cartItemUpdateDTO) {
        Long userId = 1L;
//        User user = userService.findById(userId);

//        findCartById(user.getId());
        findCartById(userId);

        updateQty(cartItemId, cartItemUpdateDTO);
    }

    protected void deleteItem(Long cartItemId) {
        Long userId = 1L;
//        User user = userService.findById(userId);

        // below function causing unknown issue
//        findCartById(user.getId());

        deleteItemFromCart(cartItemId);
    }

    public void emptyCart(OrderMessage orderMessage) {
        System.out.println("bro salman " + orderMessage.getCartId());
        cartItemRepository.deleteByCartId(orderMessage.getCartId());
    }

    private void addItemToCart(CartItemCreationDTO cartItemCreationDTO, Cart userCart) {
        boolean exists = isItemAlreadyInCart(
                userCart.getCartItems(),
                cartItemCreationDTO.getProduct_id()
        );

        if (exists) {
            throw new CartItemAlreadyExistsException("Item already exists in the cart");
        }

        CartItem cartItem = new CartItem();

        Product product = restTemplate.getForObject(
                "http://PRODUCT:8093/api/products/" + cartItemCreationDTO.getProduct_id(),
                Product.class
        );

        if (product != null) {
            cartItem.setCart(userCart);
            cartItem.setProductId(product.getId());
            cartItem.setQty(cartItemCreationDTO.getQty());
            cartItem.setPrice(product.getPrice());

            cartItemRepository.save(cartItem);
        }
    }

    private boolean isItemAlreadyInCart(List<CartItem> cartItems, Long productId) {
        return cartItems
                .stream()
                .anyMatch(item ->
                        Objects.equals(
                                item.getProductId(), productId
                        )
                );
    }

    private void updateQty(Long cartItemId, CartItemUpdateDTO cartItemUpdateDTO) {
        CartItem cartItem = findById(cartItemId);
        cartItem.setQty(cartItemUpdateDTO.getQty());

        cartItemRepository.save(cartItem);
    }

    private void deleteItemFromCart(Long cartItemId) {
        CartItem cartItem = findById(cartItemId);

        cartItemRepository.deleteById(cartItem.getId());
    }

    private CartResponse convertToDTO(Cart cart) {
        if (cart == null) {
            return new CartResponse();
        }

//        UserResponse userResponse = null;
//        User user = cart.getUser();
//        if (user != null) {
//            userResponse = new UserResponse(
//                    user.getId(),
//                    user.getFirstName(),
//                    user.getLastName(),
//                    user.getEmail(),
//                    user.getPhone()
//            );
//        }

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

    public CartItem findById(Long id) {
        return cartItemRepository
                .findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("No cart item found against this id: " + id));
    }

    private void findCartById(Long userId) {
        Cart userCart = cartRepository.findByUserId(userId);
        if (userCart == null) {
            throw new CartNotFoundException("No cart found against user");
        }
    }
}

package com.microservice_ecommerce.cart.cart;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    protected CartRepository cartRepository;
    protected CartItemRepository cartItemRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
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

    private void addItemToCart(CartItemCreationDTO cartItemCreationDTO, Cart userCart) {
//        CartItem cartItem = new CartItem();

//        Product product = productService.findById(cartItemCreationDTO.getProduct_id());

//        cartItem.setCart(userCart);
//        cartItem.setProduct(product);
//        cartItem.setQty(cartItemCreationDTO.getQty());
//        cartItem.setPrice(product.getPrice());

//        cartItemRepository.save(cartItem);
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
//                        ProductResponse productResponse = null;
//                        Product product = cartItem.getProduct();
//                        if (product != null) {
//                            productResponse = new ProductResponse(
//                                    product.getId(),
//                                    product.getName(),
//                                    product.getPrice(),
//                                    product.getSku(),
//                                    product.getQty(),
//                                    product.getInStock(),
//                                    null,
//                                    null
//                            );
//                        }

                        return new CartItemResponse(
                                cartItem.getId(),
                                cartItem.getProductId(),
                                cartItem.getQty(),
                                cartItem.getPrice(),
                                cartItem.getCreatedAt(),
                                cartItem.getUpdatedAt()
                        );
                    })

                    .toList();
        }

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                cart.getTotal(),
                cartItemResponses,
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
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

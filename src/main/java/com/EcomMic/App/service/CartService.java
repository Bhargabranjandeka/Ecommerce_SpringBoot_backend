package com.EcomMic.App.service;

import com.EcomMic.App.dto.CartItemRequest;
import com.EcomMic.App.model.CartItem;
import com.EcomMic.App.model.Product;
import com.EcomMic.App.model.User;
import com.EcomMic.App.repositories.CartRepository;
import com.EcomMic.App.repositories.ProductRepository;
import com.EcomMic.App.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public boolean addCart(String userId, CartItemRequest cartItemRequest) {
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if(productOpt.isEmpty()) return false;
        Product product = productOpt.get();
        if(product.getQuantity() < cartItemRequest.getQuantity()){
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) return false;
        User user = userOpt.get();

        CartItem existingCartItem = cartRepository.findByUserAndProduct(user,product);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteCart(String userId, String productId) {
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if(productOpt.isPresent() && userOpt.isPresent()){
            User user = userOpt.get();
            Product product = productOpt.get();
            cartRepository.deleteByUserAndProduct(user,product);
            return true;
        }

        return false;
    }

    public List<CartItem> getCartByUser(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartRepository::findByUser).orElseGet(List::of);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}

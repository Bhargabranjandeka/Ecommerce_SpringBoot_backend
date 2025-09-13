package com.EcomMic.App.controller;

import com.EcomMic.App.dto.CartItemRequest;
import com.EcomMic.App.model.CartItem;
import com.EcomMic.App.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> createCart(@RequestHeader("X-User-ID") String userId,
                                           @RequestBody CartItemRequest cartItemRequest){
        if(!cartService.addCart(userId,cartItemRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock please try later");
        };
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteCart(@RequestHeader("X-User-ID") String userId, @PathVariable String productId){
        boolean deleted = cartService.deleteCart(userId,productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }
}

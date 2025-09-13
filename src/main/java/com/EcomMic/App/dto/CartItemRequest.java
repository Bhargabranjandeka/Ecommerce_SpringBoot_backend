package com.EcomMic.App.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}

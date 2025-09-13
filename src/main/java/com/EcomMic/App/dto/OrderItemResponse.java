package com.EcomMic.App.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}

package com.EcomMic.App.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private String category;
}

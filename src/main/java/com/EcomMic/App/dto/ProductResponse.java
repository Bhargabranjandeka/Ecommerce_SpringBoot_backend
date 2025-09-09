package com.EcomMic.App.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long  id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private String category;
    private boolean active;
}

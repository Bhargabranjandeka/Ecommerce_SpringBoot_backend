package com.EcomMic.App.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private BigDecimal price;
    private Integer Quantity;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

}
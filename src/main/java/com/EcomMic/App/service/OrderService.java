package com.EcomMic.App.service;

import com.EcomMic.App.dto.OrderItemResponse;
import com.EcomMic.App.dto.OrderResponse;
import com.EcomMic.App.model.*;
import com.EcomMic.App.repositories.OrderRepository;
import com.EcomMic.App.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItem> cartItems = cartService.getCartByUser(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return Optional.empty();
        }
        User user = userOpt.get();

        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        String.valueOf(item.getProduct().getId()),
                        item.getPrice(),
                        item.getQuantity(),
                        order
                )).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(Long.valueOf(userId));

        return Optional.of(mapToOderResponse(savedOrder));
    }

    private OrderResponse mapToOderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getItems().stream().map(orderItem -> new OrderItemResponse(
                        orderItem.getId(),
                        orderItem.getProductId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice()
                )).toList(),
                savedOrder.getCreatedAt()
        );
    }
}

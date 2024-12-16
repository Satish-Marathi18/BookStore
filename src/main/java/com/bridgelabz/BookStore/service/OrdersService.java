package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.OrderRequestDTO;
import com.bridgelabz.BookStore.dto.OrdersResponseDTO;

import java.util.List;

public interface OrdersService {
    OrdersResponseDTO PlaceOrderByCartId(Long userId, OrderRequestDTO orderRequestDTO, Long cartId);

    OrdersResponseDTO orderAllCartItems(Long userId, OrderRequestDTO orderRequestDTO);

    String cancelOrder(Long userId, Long orderId);

    List<OrdersResponseDTO> getAllOrders();

    List<OrdersResponseDTO> getUserOrders(Long userId);
}

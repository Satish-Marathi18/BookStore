package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.CartRequestDTO;
import com.bridgelabz.BookStore.dto.CartResponseDTO;

import java.util.List;

public interface CartService {
    CartResponseDTO addToCart(Long userId, CartRequestDTO cartRequestDTO);

    String removeCartByUser(Long userId, Long cartId);

    String removeFromCart(Long cartId);

    CartResponseDTO updateQuantity(Long userId, Long cartId, Long quantity);

    List<CartResponseDTO> getAllCartItemsForUser(Long userId);

    List<CartResponseDTO> getAllCartItems();
}

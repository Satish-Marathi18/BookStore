package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.dto.WishListResponseDTO;

import java.util.List;

public interface WishListService {

    WishListResponseDTO addToWishList(Long userId, Long bookId);

    List<WishListResponseDTO> getUserWishList(Long userId);

    String removeFromWishList(Long userId, Long bookId);

    CartResponseDTO addToCart(Long userId, Long wishListId);
}

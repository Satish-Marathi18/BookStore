package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.dto.WishListResponseDTO;
import com.bridgelabz.BookStore.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filterwishlist")
public class SecureWishListController {
    private WishListService wishListService;
    public SecureWishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("/addwishlist")
    public ResponseEntity<WishListResponseDTO> addToWishList(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @RequestParam Long bookId) {
        if (role.equalsIgnoreCase("user")) {
            return new ResponseEntity<>(wishListService.addToWishList(userId,bookId), HttpStatus.OK);
        }
        throw new RuntimeException("Admin can't add to wish list");
    }

    @GetMapping("/getuserwishlist")
    public ResponseEntity<List<WishListResponseDTO>> getUserWishList(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role) {
        if(role.equalsIgnoreCase("user")) {
            return new ResponseEntity<>(wishListService.getUserWishList(userId),HttpStatus.OK);
        }
        throw new RuntimeException("Admin can't get wish list");
    }

    @DeleteMapping("/removewishlist")
    public ResponseEntity<String> removeFromWishList(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @RequestParam Long bookId) {
        if(role.equalsIgnoreCase("user")) {
            return new ResponseEntity<>(wishListService.removeFromWishList(userId,bookId),HttpStatus.OK);
        }
        throw new RuntimeException("Admin can't remove from wish list");
    }

    @PostMapping("/addtocart")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestAttribute("id") Long userId, @RequestParam Long wishListId) {
        return new ResponseEntity<>(wishListService.addToCart(userId, wishListId), HttpStatus.CREATED);
    }
}

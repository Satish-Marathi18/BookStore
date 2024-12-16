package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping("/removecart")
    public ResponseEntity<?> removeFromCart(@RequestParam Long cartId) {
        return new ResponseEntity<>(cartService.removeFromCart(cartId), HttpStatus.OK);
    }

    @GetMapping("/getallcartitems")
    public ResponseEntity<List<CartResponseDTO>> getAllCartItems() {
        return new ResponseEntity<>(cartService.getAllCartItems(), HttpStatus.OK);
    }
}

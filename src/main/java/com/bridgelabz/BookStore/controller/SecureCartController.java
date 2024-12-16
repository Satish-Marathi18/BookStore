package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.CartRequestDTO;
import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.repo.CartRepo;
import com.bridgelabz.BookStore.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class SecureCartController {
    private final CartRepo cartRepo;
    private CartService cartService;
    public SecureCartController(CartService cartService, CartRepo cartRepo) {
        this.cartService = cartService;
        this.cartRepo = cartRepo;
    }

    @PostMapping("/addtocart")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @RequestBody CartRequestDTO cartRequestDTO) {
        if("ADMIN".equalsIgnoreCase(role)) {
            throw new IllegalArgumentException("Admin can't add to a cart.");
        }
        return new ResponseEntity<>(cartService.addToCart(userId,cartRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/removebyuser")
    public ResponseEntity<?> removeCartByUser(@RequestAttribute("id") Long userId, @RequestAttribute("role") String role, @RequestParam Long cartId) {
        if("USER".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(cartService.removeCartByUser(userId, cartId), HttpStatus.OK);
        }
        else
            throw new IllegalArgumentException("Admin can't remove a cart.");
    }

    @PutMapping("/updatequantity")
    public ResponseEntity<CartResponseDTO> updateQuantity(@RequestAttribute("id") Long userId, @RequestParam Long cartId, @RequestParam Long quantity) {
        return new ResponseEntity<>(cartService.updateQuantity(userId, cartId, quantity), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getusercartitems")
    public ResponseEntity<List<CartResponseDTO>> getAllCartItemsForUser(@RequestAttribute("id") Long userId) {
        return new ResponseEntity<>(cartService.getAllCartItemsForUser(userId), HttpStatus.OK);
    }
}

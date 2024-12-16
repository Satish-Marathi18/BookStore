package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.OrderRequestDTO;
import com.bridgelabz.BookStore.dto.OrdersResponseDTO;
import com.bridgelabz.BookStore.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/filterorder")
public class SecureOrderController {
    private OrdersService ordersService;
    public SecureOrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrdersResponseDTO> placeOrderByCartId(@RequestAttribute("id") Long userId, @RequestBody OrderRequestDTO orderRequestDTO, @RequestParam Long cartId) {
        return new ResponseEntity<>(ordersService.PlaceOrderByCartId(userId, orderRequestDTO, cartId), HttpStatus.OK);
    }

    @PostMapping("/orderall")
    public ResponseEntity<OrdersResponseDTO> placeOrder(@RequestAttribute("id") Long userId, @RequestBody OrderRequestDTO orderRequestDTO) {
        return new ResponseEntity<>(ordersService.orderAllCartItems(userId, orderRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@RequestAttribute("id") Long userId, @PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(ordersService.cancelOrder(userId, orderId), HttpStatus.OK);
    }

    @GetMapping("/getallorders")
    public ResponseEntity<List<OrdersResponseDTO>> getAllOrders(@RequestAttribute("role") String role) {
        if(role.equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
        }
        throw new RuntimeException("Only admin can get all orders");
    }

    @GetMapping("/getuserorders")
    public ResponseEntity<List<OrdersResponseDTO>> getUserOrders(@RequestAttribute("role") String role, @RequestAttribute("id") Long userId) {
        if(role.equalsIgnoreCase("user")) {
            return new ResponseEntity<>(ordersService.getUserOrders(userId), HttpStatus.OK);
        }
        throw new RuntimeException("Only User can get all orders");
    }
}

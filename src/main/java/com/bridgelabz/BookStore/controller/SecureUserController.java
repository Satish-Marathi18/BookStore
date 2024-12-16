package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.UserRequestDTO;
import com.bridgelabz.BookStore.dto.UserResponseDTO;
import com.bridgelabz.BookStore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filter")
public class SecureUserController {
    private UserService userService;
    public SecureUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<UserResponseDTO> getUserByToken(@RequestAttribute("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestAttribute("id") Long id, @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.updateUser(id, userRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestAttribute("id") Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.ACCEPTED);
    }
}

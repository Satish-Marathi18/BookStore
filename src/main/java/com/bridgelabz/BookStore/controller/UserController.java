package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.*;
import com.bridgelabz.BookStore.entity.User;
import com.bridgelabz.BookStore.service.UserService;
import com.bridgelabz.BookStore.util.TokenUtility;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private TokenUtility tokenUtility;
    public UserController(UserService userService, TokenUtility tokenUtility) {
        this.userService = userService;
        this.tokenUtility = tokenUtility;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.registerUser(userRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = userService.loginUser(loginRequestDTO);
        String token = tokenUtility.generateToken(user.getEmail(),user.getPassword(),user.getRole(),user.getId());
        JWTTokenDTO jwtTokenDTO = new JWTTokenDTO(token);
        return new ResponseEntity<>(jwtTokenDTO, HttpStatus.OK);
    }

    @PutMapping("/resetpassword")
    public ResponseEntity<UserResponseDTO> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return new ResponseEntity<>(userService.resetPassword(resetPasswordDTO),HttpStatus.OK);
    }

    @GetMapping("/forgotpassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Testing successful....",HttpStatus.OK);
    }



}

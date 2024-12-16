package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.LoginRequestDTO;
import com.bridgelabz.BookStore.dto.ResetPasswordDTO;
import com.bridgelabz.BookStore.dto.UserRequestDTO;
import com.bridgelabz.BookStore.dto.UserResponseDTO;
import com.bridgelabz.BookStore.entity.User;

public interface UserService {
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    User loginUser(LoginRequestDTO loginRequestDTO);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    String deleteUser(Long id);

    UserResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO);

    String forgotPassword(String email);
}

package com.bridgelabz.BookStore.mapper;

import com.bridgelabz.BookStore.dto.BookResponseDTO;
import com.bridgelabz.BookStore.dto.UserResponseDTO;
import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.User;

public class Mapper {
    public static BookResponseDTO mapBookToResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setBookName(book.getBookName());
        dto.setBookAuthor(book.getBookAuthor());
        dto.setBookPrice(book.getBookPrice());
        dto.setQuantity(book.getQuantity());
        dto.setBookLogo("Book Logo");
        dto.setBookDescription(book.getBookDescription());
        return dto;
    }

    public static UserResponseDTO mapUserToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setDob(user.getDob());
        userResponseDTO.setRegisteredDate(user.getRegisteredDate());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUpdatedDate(user.getUpdatedDate());
        userResponseDTO.setPassword(user.getPassword());
        return userResponseDTO;
    }
}

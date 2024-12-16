package com.bridgelabz.BookStore.controller;

import com.bridgelabz.BookStore.dto.BookResponseDTO;
import com.bridgelabz.BookStore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/filter")
public class SecureBookController {
    private BookService bookService;
    public SecureBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addbook")
    public ResponseEntity<BookResponseDTO> addBook(@RequestAttribute("role") String role,
                                                   @RequestParam("bookName") String bookName,
                                                   @RequestParam("bookAuthor") String bookAuthor,
                                                   @RequestParam("bookDescription") String bookDescription,
                                                   @RequestParam("bookLogo") MultipartFile bookLogo,
                                                   @RequestParam("bookPrice") Double bookPrice,
                                                   @RequestParam("quantity") Long quantity) throws IOException {
        if(role == null) {
            throw new IllegalArgumentException("Users cannot add books, Only admin can add.");
        }
        if(role.equalsIgnoreCase("ADMIN")) {
            return new ResponseEntity<>(bookService.addBook(bookName, bookAuthor, bookDescription, bookLogo, bookPrice, quantity), HttpStatus.CREATED);
        }
        else {
            throw new IllegalArgumentException("Users cannot add book");
        }
    }

    @PutMapping("/updatebook")
    public ResponseEntity<BookResponseDTO> updateBookDetails(@RequestAttribute("role") String role,
                                                             @RequestParam("bookName") String bookName,
                                                             @RequestParam("bookAuthor") String bookAuthor,
                                                             @RequestParam("bookDescription") String bookDescription,
                                                             @RequestParam("bookLogo") MultipartFile bookLogo) throws IOException {
            if(role == null || role.equalsIgnoreCase("User")) {
                throw new IllegalArgumentException("Users cannot update book, Only admin can update book.");
            }
                return new ResponseEntity<>(bookService.updateBookDetails(bookName, bookAuthor, bookDescription, bookLogo), HttpStatus.OK);
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity<String> removeBookById(@RequestAttribute("role") String role, @PathVariable Long id) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookService.removeBook(id), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot remove book");
        }
    }

    @PutMapping("/changequantity")
    public ResponseEntity<BookResponseDTO> changeBookQuantity(@RequestAttribute("role") String role, @RequestParam Long bookId, @RequestParam Long quantity) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookService.changeBookQuantity(bookId, quantity), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book quantity");
        }
    }

    public ResponseEntity<BookResponseDTO> changeBookPrice(@RequestAttribute("role") String role, @RequestParam Long bookId, @RequestParam Double price) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookService.changeBookPrice(bookId, price), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book price");
        }
    }

}

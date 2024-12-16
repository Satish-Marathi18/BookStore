package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.BookRequestDTO;
import com.bridgelabz.BookStore.dto.BookResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    BookResponseDTO addBook(String bookName, String bookAuthor, String bookDescription,
                            MultipartFile bookLogo, Double bookPrice, Long quantity) throws IOException;

    BookResponseDTO updateBookDetails(String bookName, String bookAuthor, String bookDescription, MultipartFile bookLogo) throws IOException;

    String removeBook(Long id);

    BookResponseDTO changeBookQuantity(Long bookId, Long quantity);

    BookResponseDTO changeBookPrice(Long bookId, Double price);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookById(Long id);
}

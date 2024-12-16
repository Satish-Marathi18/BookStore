package com.bridgelabz.BookStore.serviceImple;

import com.bridgelabz.BookStore.dto.BookRequestDTO;
import com.bridgelabz.BookStore.dto.BookResponseDTO;
import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.repo.BookRepo;
import com.bridgelabz.BookStore.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private BookRepo bookRepo;
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public BookResponseDTO addBook(String bookName, String bookAuthor, String bookDescription,
                                   MultipartFile bookLogo, Double bookPrice, Long quantity) throws IOException {
        Boolean isPresent = bookRepo.existsByBookNameIgnoreCase(bookName);
        if (isPresent) {
            throw new IllegalArgumentException("Book already exists");
        }
        Book book = new Book();
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);
        book.setBookDescription(bookDescription);
        book.setBookLogo(bookLogo.getBytes());
        book.setBookPrice(bookPrice);
        book.setQuantity(quantity);
        book = bookRepo.save(book);
        return mapToResponseDTO(book);
    }

    @Override
    public BookResponseDTO updateBookDetails(String bookName, String bookAuthor, String bookDescription, MultipartFile bookLogo) throws IOException {
        Book book = bookRepo.findByBookNameIgnoreCase(bookName).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if (book == null) {
            throw new IllegalArgumentException("Book does not exist");
        }
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);
        book.setBookDescription(bookDescription);
        book.setBookLogo(bookLogo.getBytes());
        book = bookRepo.save(book);
        return mapToResponseDTO(book);
    }

    @Override
    public String removeBook(Long id) {
        Boolean isPresent = bookRepo.existsById(id);
        if (!isPresent) {
            throw new IllegalArgumentException("Book not found");
        }
        bookRepo.deleteById(id);
        return "Book removed successfully";
    }

    @Override
    public BookResponseDTO changeBookQuantity(Long bookId, Long quantity) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.setQuantity(quantity);
        return mapToResponseDTO(bookRepo.save(book));
    }

    @Override
    public BookResponseDTO changeBookPrice(Long bookId, Double price) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if(book != null) {
            book.setBookPrice(price);
        }
        return mapToResponseDTO(bookRepo.save(book));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
       return bookRepo.findAll().stream().map(book -> mapToResponseDTO(book)).collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return mapToResponseDTO(book);
    }

    private BookResponseDTO mapToResponseDTO(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setBookName(book.getBookName());
        bookResponseDTO.setBookAuthor(book.getBookAuthor());
        bookResponseDTO.setBookDescription(book.getBookDescription());
        bookResponseDTO.setBookPrice(book.getBookPrice());
        bookResponseDTO.setQuantity(book.getQuantity());
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setBookLogo(null);
        return bookResponseDTO;
    }




}

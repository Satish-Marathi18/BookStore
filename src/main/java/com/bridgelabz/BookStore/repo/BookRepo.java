package com.bridgelabz.BookStore.repo;

import com.bridgelabz.BookStore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Boolean existsByBookNameIgnoreCase(String bookName);

    Optional<Book> findByBookNameIgnoreCase(String bookName);
}

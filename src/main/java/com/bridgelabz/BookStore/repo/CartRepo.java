package com.bridgelabz.BookStore.repo;

import com.bridgelabz.BookStore.dto.CartResponseDTO;
import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.Cart;
import com.bridgelabz.BookStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByBookAndUser(Book book, User user);

    List<Cart> findByUser(User user);

    Boolean existsByUser(User user);
}

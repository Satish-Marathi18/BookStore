package com.bridgelabz.BookStore.repo;

import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.Feedback;
import com.bridgelabz.BookStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByBookAndUser(Book book, User user);
}

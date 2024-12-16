package com.bridgelabz.BookStore.repo;

import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.User;
import com.bridgelabz.BookStore.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepo extends JpaRepository<WishList, Long> {
    Boolean existsWishListByBook(Book book);

    boolean existsByBook(Book book);

    List<WishList> findByUser(User user);

    Optional<WishList> findByBookAndUser(Book book, User user);

    boolean existsByIdAndUser(Long wishListId, User user);
}

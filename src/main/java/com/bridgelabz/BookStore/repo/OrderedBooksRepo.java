package com.bridgelabz.BookStore.repo;

import com.bridgelabz.BookStore.entity.OrderedBooks;
import com.bridgelabz.BookStore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderedBooksRepo extends JpaRepository<OrderedBooks, Long> {

    List<OrderedBooks> findByOrder(Orders order);
}

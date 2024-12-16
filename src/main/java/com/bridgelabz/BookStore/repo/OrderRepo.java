package com.bridgelabz.BookStore.repo;

import com.bridgelabz.BookStore.entity.Orders;
import com.bridgelabz.BookStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    Boolean existsByUser(User user);

    List<Orders> findByCancel(boolean b);

    List<Orders> findByUser(User user);
}

package com.bridgelabz.BookStore.dto;

import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponseDTO {
    private Long id;
    private LocalDate orderDate;
    private Double price;
    private Long quantity;
    private String address;
    private User user;
    private List<BookResponseDTO> book = new ArrayList<BookResponseDTO>();
    private Boolean cancel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookResponseDTO> getBook() {
        return book;
    }

    public void setBook(List<BookResponseDTO> book) {
        this.book = book;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }
}

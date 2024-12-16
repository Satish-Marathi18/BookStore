package com.bridgelabz.BookStore.dto;

import com.bridgelabz.BookStore.entity.Book;
import com.bridgelabz.BookStore.entity.User;

public class WishListResponseDTO {
    private BookResponseDTO book;
    private User user;
    private String isAvailable;

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public BookResponseDTO getBook() {
        return book;
    }

    public void setBook(BookResponseDTO book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

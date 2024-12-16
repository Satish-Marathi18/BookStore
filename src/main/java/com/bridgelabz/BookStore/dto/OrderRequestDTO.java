package com.bridgelabz.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private String address;

    public String getAddress() {
        return address;
    }
}

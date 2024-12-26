package com.bridgelabz.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class JWTTokenDTO {
    private String token;

    public String getToken() {
        return token;
    }

    public JWTTokenDTO() {
    }

    public JWTTokenDTO(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

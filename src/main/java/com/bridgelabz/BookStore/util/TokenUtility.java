package com.bridgelabz.BookStore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtility {
    private static final String SECRET = "Secret";
    private static final long EXPIRY_TIME = 1000*60*60;

    public String generateToken(String email, String password, String role, Long id) {
        return JWT.create()
                .withClaim("email",email)
                .withClaim("role",role)
                .withClaim("id",id)
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRY_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String getEmailFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("email")
                .asString();
    }

    public Long getUserIdFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("id")
                .asLong();
    }

    public String getRoleFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("role")
                .asString();
    }

    public Date getExpirationDateFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
        return jwt.getExpiresAt();
    }

}

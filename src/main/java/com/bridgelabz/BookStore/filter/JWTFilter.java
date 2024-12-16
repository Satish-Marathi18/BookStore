package com.bridgelabz.BookStore.filter;

import com.bridgelabz.BookStore.util.TokenUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JWTFilter extends HttpFilter {
    private TokenUtility tokenUtility;
    public JWTFilter(TokenUtility tokenUtility) {
        this.tokenUtility = tokenUtility;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
            String email = tokenUtility.getEmailFromToken(token);
            String role = tokenUtility.getRoleFromToken(token);
            Long userId = tokenUtility.getUserIdFromToken(token);
            request.setAttribute("email", email);
            request.setAttribute("role", role);
            request.setAttribute("id", userId);
            chain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Token");
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Authorization token is missing");
        }
    }

    @Override
    public void init() throws ServletException {}

    @Override
    public void destroy() {}


}

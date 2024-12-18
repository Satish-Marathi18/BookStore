package com.bridgelabz.BookStore.filter;

import com.bridgelabz.BookStore.util.TokenUtility;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistration(TokenUtility tokenUtility) {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilter(tokenUtility));
        registrationBean.addUrlPatterns("/filter/*","/filterorder/*","/filterwishlist/*");
        return registrationBean;
    }
}

package com.aflr.mybankbackend.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthoritiesLoggingAfterFilter implements Filter {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            LOGGER.log(Level.INFO, "User {0} is sucessfuly authenticated and has these authorities {1}",
                    new String[] {authentication.getName(), authentication.getAuthorities().toString()});
        }
        chain.doFilter(request, response);
    }
}

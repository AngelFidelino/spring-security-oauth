package com.aflr.mybankbackend.filter;



import com.aflr.mybankbackend.controllers.LoginController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestValidationBeforeFilter extends OncePerRequestFilter {
    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private static final String INVALID_USER_NAME = "test";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader(AUTHORIZATION);

        if (auth == null || !StringUtils.startsWithIgnoreCase(auth, AUTHENTICATION_SCHEME_BASIC))
            throw new BadCredentialsException("Failed to decode the auth token");

        String base64Credentials = auth.substring(AUTHENTICATION_SCHEME_BASIC.length()).strip();
        byte[] decodedCredential = Base64.getDecoder().decode(base64Credentials);
        String username = new String(decodedCredential, StandardCharsets.UTF_8).split(":")[0];
        if (username.toLowerCase().contains(INVALID_USER_NAME)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals(LoginController.USER_PATH);
    }
}

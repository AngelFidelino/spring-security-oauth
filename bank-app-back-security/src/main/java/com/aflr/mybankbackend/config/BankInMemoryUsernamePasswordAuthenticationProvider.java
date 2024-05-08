package com.aflr.mybankbackend.config;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
/**
 * This class should be intent to use when need to create our own authentication provider
 * Ingore this class when using OUATH
 * */
@Component
public class BankInMemoryUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Optional<UserDetails> userOpt = getUsers(username);
        if (!userOpt.isPresent())
            throw new UsernameNotFoundException("No user found");
        final UserDetails customer = userOpt.get();
        if (!NoOpPasswordEncoder.getInstance().matches(pwd, customer.getPassword()))
            throw new BadCredentialsException("Invalid credential");
        return new UsernamePasswordAuthenticationToken(username, pwd, customer.getAuthorities());
    }

    private Optional<UserDetails> getUsers(@NonNull String username) {
        UserDetails admin = User.withUsername("angel")
                .password("xxx")
                .authorities("ROLE_ADMIN")
                .build();
        UserDetails user = User.withUsername("fide")
                .password("xxx")
                .authorities("ROLE_ADMIN","ROLE_USER")
                .build();
        return List.of(admin, user).stream().filter(u -> username.equals(u.getUsername())).findFirst();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

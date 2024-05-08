package com.aflr.mybankbackend.config;

import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class should be intent to use when need to create our own authentication provider
 * Ingore this class when using OUATH
 * */
@Component
public class BankUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    private BankUserDetails bankUserDetails;

    public BankUsernamePasswordAuthenticationProvider(CustomerRepository cr, @Lazy PasswordEncoder pe,
            BankUserDetails ud) {
        customerRepository = cr;
        passwordEncoder = pe;
        bankUserDetails = ud;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPwd = authentication.getCredentials().toString();
        final UserDetails userDetail = bankUserDetails.loadUserByUsername(username);
        if (!passwordEncoder.matches(rawPwd, userDetail.getPassword()))
            throw new BadCredentialsException("Invalid credential");
        return new UsernamePasswordAuthenticationToken(username, userDetail.getPassword(), userDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

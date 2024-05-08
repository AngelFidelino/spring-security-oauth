package com.aflr.mybankbackend.config;

import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("bankUserDetails")
public class BankUserDetails { //implements UserDetailsService {
    private CustomerRepository customerRepository;

    public BankUserDetails(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customers = customerRepository.findByEmail(username);
        if (customers.isEmpty())
            throw new UsernameNotFoundException("user not found: " + username);
        final Customer customer = customers.get(0);
        List<GrantedAuthority> authorities =
                customer.getAuthorities().stream().map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList());
        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }

}

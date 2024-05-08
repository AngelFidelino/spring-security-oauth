package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    public CustomerController(CustomerRepository customerRepository,PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
        return customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }



}

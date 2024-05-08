package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LoginController {
    public static final String REGISTER_PATH = "/register";
    public static final String USER_PATH = "/user";

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    public LoginController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Example: http://localhost:8080/register
          {
              "name":"Milca",
              "email": "milca@hotmail.com",
              "mobileNumber":"73748384",
              "pwd": "123",
              "role": "admin"
          }
     * */
    @PostMapping(REGISTER_PATH)
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(LocalDateTime.now());
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }
    @GetMapping(USER_PATH)
    public Customer getUserDetails(Authentication authentication) {
        final List<Customer> customers = customerRepository.findByEmail(authentication.getName());
        if (!customers.isEmpty()) {
            return customers.get(0);
        }
        return null;
    }
}

package com.aflr.mybankbackend.controllers;


import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.entities.Loans;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import com.aflr.mybankbackend.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class LoansController {
    private CustomerRepository customerRepository;

    public LoansController(LoanRepository loanRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
    }

    private LoanRepository loanRepository;

    /**
     * Example: http://localhost:8080/myLoans?id=1
     */
    @GetMapping(value = "/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email, Authentication authentication) {
        log.info("authentication: {}", authentication.getName());
        Optional<Customer> customer = customerRepository.findOneByEmail(email);
        if (!customer.isPresent())
            return Collections.emptyList();
        return loanRepository.findByCustomerIdOrderByStartDtDesc(customer.get().getId());
    }

}

package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.Accounts;
import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.AccountsRepository;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/myAccount")
public class AccountController {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    public AccountController(AccountsRepository accountsRepository, CustomerRepository customerRepository) {
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public Accounts getAccountDetails(@RequestParam String email) {
        Optional<Customer> customer = customerRepository.findOneByEmail(email);
        if (!customer.isPresent())
            return null;
        List<Accounts> accounts = accountsRepository.findOneByCustomerId(customer.get().getId());
        if (!accounts.isEmpty()) {
            return accounts.get(0);
        } else {
            return null;
        }
    }

    @PostMapping
    public Accounts addAccountDetails(@RequestBody Accounts account) {
        return accountsRepository.save(account);
    }

}

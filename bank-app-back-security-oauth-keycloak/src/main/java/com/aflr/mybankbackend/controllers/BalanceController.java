package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.AccountTransactions;
import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.AccountTransactionsRepository;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class BalanceController {
    private AccountTransactionsRepository accountTransactionsRepository;
    private CustomerRepository customerRepository;

    public BalanceController(AccountTransactionsRepository accountTransactionsRepository,
            CustomerRepository customerRepository) {
        this.accountTransactionsRepository = accountTransactionsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam("email") String email) {
        Optional<Customer> customer = customerRepository.findOneByEmail(email);
        if (!customer.isPresent())
            return Collections.emptyList();
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(customer.get().getId());
        if (accountTransactions != null) {
            return accountTransactions;
        } else {
            return Collections.emptyList();
        }
    }

}

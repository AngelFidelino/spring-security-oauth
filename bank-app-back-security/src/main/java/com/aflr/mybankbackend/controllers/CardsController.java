package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.Cards;
import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.CardsRepository;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CardsController {

    private CardsRepository cardsRepository;
    private CustomerRepository customerRepository;

    public CardsController(CardsRepository cardsRepository, CustomerRepository customerRepository) {
        this.cardsRepository = cardsRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Optional<Customer> customer = customerRepository.findOneByEmail(email);
        if (!customer.isPresent())
            return null;
        return cardsRepository.findByCustomerId(customer.get().getId());
    }

}

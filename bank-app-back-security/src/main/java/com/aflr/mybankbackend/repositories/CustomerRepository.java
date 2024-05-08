package com.aflr.mybankbackend.repositories;

import com.aflr.mybankbackend.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findByEmail(String email);
    Optional<Customer> findOneByEmail(String email);
}


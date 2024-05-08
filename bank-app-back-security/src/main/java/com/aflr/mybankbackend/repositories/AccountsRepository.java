package com.aflr.mybankbackend.repositories;

import com.aflr.mybankbackend.entities.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
    List<Accounts> findOneByCustomerId(int customerId);
}

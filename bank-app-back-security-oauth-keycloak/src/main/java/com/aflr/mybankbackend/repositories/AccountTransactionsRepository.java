package com.aflr.mybankbackend.repositories;

import com.aflr.mybankbackend.entities.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, String> {
    List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);
}

package com.aflr.mybankbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class AccountTransactions {
    @Id
    private String transactionId;

    private long accountNumber;

    private int customerId;

    private LocalDate transactionDt;

    private String transactionSummary;

    private String transactionType;

    private int transactionAmt;

    private int closingBalance;

    private LocalDate createDt;
}

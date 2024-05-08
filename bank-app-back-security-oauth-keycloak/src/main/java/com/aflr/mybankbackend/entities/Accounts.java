package com.aflr.mybankbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Accounts extends BaseEntity{
    @Id
    private long accountNumber;
    private int customerId;
    private String accountType;
    private String branchAddress;
}

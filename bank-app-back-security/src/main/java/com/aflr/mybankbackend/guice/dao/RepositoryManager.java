package com.aflr.mybankbackend.guice.dao;


import com.aflr.mybankbackend.guice.dto.FailedTransaction;
import com.aflr.mybankbackend.guice.dto.NormalizedTransaction;

import java.sql.SQLException;

public class RepositoryManager {
    public boolean update(NormalizedTransaction normalizedTransaction) throws SQLException {
        return true;
    }
    public boolean upsert(FailedTransaction failedTransaction) throws SQLException {
        return false;
    }
}

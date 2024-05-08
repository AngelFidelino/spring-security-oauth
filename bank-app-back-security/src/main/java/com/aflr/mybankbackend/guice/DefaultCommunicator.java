package com.aflr.mybankbackend.guice;

import com.aflr.mybankbackend.guice.dao.RepositoryManager;
import com.aflr.mybankbackend.guice.dto.NormalizedTransaction;
import com.google.inject.Inject;

import java.sql.SQLException;

public class DefaultCommunicator implements Communicator{
    @Inject
    protected RepositoryManager repositoryManager;

    public DefaultCommunicator() {
    }


    public boolean sendMessage(NormalizedTransaction normalizedTransaction) {
        try {
            repositoryManager.update(normalizedTransaction);
            return repositoryManager.upsert(normalizedTransaction.getFailedTransaction());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean sendMessageByDefault(String message) {
        return message.contains("sendMessageByDefault");
    }
}

package com.aflr.mybankbackend.guice;

import com.aflr.mybankbackend.guice.dto.NormalizedTransaction;
import com.google.inject.Inject;

import java.util.logging.Logger;

public class Communication {

    @Inject
    private Logger logger;

    @Inject
    private Communicator communicator;

    public Communication() {
    }
    public Communication(Boolean keepRecords) {
        if (keepRecords) {
            System.out.println("Message logging enabled");
        }
    }

    public boolean sendMessage(NormalizedTransaction normalizedTransaction) {
        System.out.println("Running sendMessage"+ communicator);
        return communicator.sendMessage(normalizedTransaction);
    }

    public Communicator getCommunicator() {
        return this.communicator;
    }
}

package com.aflr.mybankbackend.guice;

import com.aflr.mybankbackend.guice.dto.NormalizedTransaction;

public interface Communicator {
    boolean sendMessage(NormalizedTransaction normalizedTransaction);
}

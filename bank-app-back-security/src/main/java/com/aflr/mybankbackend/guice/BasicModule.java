package com.aflr.mybankbackend.guice;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(Communicator.class).to(DefaultCommunicator.class);
    }
}

package com.aflr.mybankbackend.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class AOPModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(
                Matchers.any(),
                Matchers.any(),
                new MessageLogger()
        );
    }
}

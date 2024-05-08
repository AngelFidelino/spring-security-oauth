package com.aflr.mybankbackend.guice;

import com.aflr.mybankbackend.guice.dto.FailedTransaction;
import com.aflr.mybankbackend.guice.dto.NormalizedTransaction;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args){
        Injector injector = Guice.createInjector(new BasicModule(), new AOPModule());
        Communication com = injector.getInstance(Communication.class);

        NormalizedTransaction normalizedTransaction = new NormalizedTransaction(123432,3);
        //normalizedTransaction.setFailedTransaction(new FailedTransaction());
        boolean bool = com.sendMessage(normalizedTransaction);
        System.out.println("bool: "+bool);
    }
}

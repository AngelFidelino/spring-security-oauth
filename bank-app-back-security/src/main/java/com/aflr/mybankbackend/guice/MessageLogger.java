package com.aflr.mybankbackend.guice;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MessageLogger implements MethodInterceptor {

    final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("Entered into the method -> " + invocation.getMethod().getDeclaringClass().getName()+"."+invocation.getMethod().getName()
                + " and input arguments are -> " + convertArgumentList(Arrays.asList(invocation.getArguments())));
        return invocation.proceed();
    }
    private String convertArgumentList(List<Object> args){

        StringBuilder sb = new StringBuilder();

        for(Object arg : args){
            if(arg==null){
                System.out.printf("NULLLLLLLLLLLLLLL");
            }
            sb.append(arg.toString());
        }

        return sb.toString();
    }
}

package com.aflr.mybankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class MyBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBankBackendApplication.class, args);
    }

}

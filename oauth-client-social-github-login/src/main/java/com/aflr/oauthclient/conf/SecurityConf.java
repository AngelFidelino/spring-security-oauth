package com.aflr.oauthclient.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConf {
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }
/*
    @Bean
    public ClientRegistrationRepository clientRepository() {
        ClientRegistration clientRegistration = getClientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    private ClientRegistration getClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("xxx")
                .clientSecret("xxx").build();
    }*/

}

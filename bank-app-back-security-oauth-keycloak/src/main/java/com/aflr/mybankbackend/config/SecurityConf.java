package com.aflr.mybankbackend.config;


import com.aflr.mybankbackend.controllers.LoginController;
import com.aflr.mybankbackend.enums.Roles;
import com.aflr.mybankbackend.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConf {
    private String[] authenticatedPaths =
            {"/myAccount", "/myBalance", "/myLoans", "/myCards", LoginController.USER_PATH,
                    LoginController.REGISTER_PATH, "/customer", "/h2-console"};
    private String[] nonAuthenticatedPaths = {"/notices", "/contact", LoginController.REGISTER_PATH};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain_OAUTH(HttpSecurity http)
            throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRehHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRehHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return http.authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/myAccount").hasRole(Roles.USER.name())
                            .requestMatchers("/myBalance").hasAnyRole(Roles.USER.name(), Roles.ADMIN.name())
                            .requestMatchers("/myLoans").authenticated()
                            .requestMatchers("/myCards").hasRole(Roles.USER.name())
                            .requestMatchers(LoginController.USER_PATH, LoginController.REGISTER_PATH, "/customer")
                            .authenticated()
                            .requestMatchers(nonAuthenticatedPaths).permitAll()
                            .requestMatchers(toH2Console()).authenticated();
                })
                .headers(headers -> headers.frameOptions(withDefaults()).disable())
                .cors(cors -> cors.configurationSource(getCorsConfigurationSource()))

                .csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenRehHandler)
                        .ignoringRequestMatchers("/contact", LoginController.REGISTER_PATH)
                        .ignoringRequestMatchers(toH2Console())
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(server -> server.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .build();
    }

    private CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of(AUTHORIZATION));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

package com.aflr.mybankbackend.config;


import com.aflr.mybankbackend.controllers.LoginController;
import com.aflr.mybankbackend.enums.Roles;
import com.aflr.mybankbackend.filter.AuthoritiesLoggingAfterFilter;
import com.aflr.mybankbackend.filter.CsrfCookieFilter;
import com.aflr.mybankbackend.filter.JWTTokenGeneratorFilter;
import com.aflr.mybankbackend.filter.JWTTokenValidatorFilter;
import com.aflr.mybankbackend.filter.RequestValidationBeforeFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
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
    public AuthenticationManager authenticationManager(HttpSecurity http,
            BankUsernamePasswordAuthenticationProvider bankUsernamePasswordAuthProvider,
            BankInMemoryUsernamePasswordAuthenticationProvider bankInMemoryUsernamePasswordAuthProvider) {
        return new ProviderManager(bankUsernamePasswordAuthProvider, bankInMemoryUsernamePasswordAuthProvider);
    }

    /**
     * Use this bean when we want to generate and validate the token. No OAUTH integration.
     */
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRehHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRehHandler.setCsrfRequestAttributeName("_csrf");

        return http.authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/myAccount").hasRole(Roles.USER.name())
                            .requestMatchers("/myBalance").hasAnyRole(Roles.USER.name(), Roles.ADMIN.name())
                            .requestMatchers("/myLoans").authenticated()
                            .requestMatchers("/myCards").hasRole(Roles.USER.name())
                            //.requestMatchers("/myAccount").hasAuthority(VIEWACCOUNT.name())
                            //.requestMatchers("/myBalance").hasAnyAuthority(VIEWACCOUNT.name(),VIEWBALANCE.name())
                            //.requestMatchers("/myLoans").hasAuthority(VIEWLOANS.name())
                            //.requestMatchers("/myCards").hasAuthority(VIEWCARDS.name())
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
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)

                //We're not sending JSESSIONID as part of the initial response because we're using JWT now
                //.securityContext(sc -> sc.requireExplicitSave(false))
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .formLogin(withDefaults())
                .httpBasic(withDefaults())
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


    /*
     * This bean is meant to be used by the default provider manager. Using it when a custom provider won't work
     * */
    @Bean(name = "jdbcUserDetailsManager")
    @ConditionalOnProperty(name = "enabled", havingValue = "true", matchIfMissing = false)
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /*
     * This bean is meant to be used by the default provider manager. Using it when a custom provider won't work
     * */
    @Bean
    @ConditionalOnProperty(name = "enabled", havingValue = "true", matchIfMissing = false)
    public UserDetailsManager userDetailsManager() {
        AuthenticationProvider ap;
        AuthenticationManager am;
        DaoAuthenticationProvider dap;
        UsernamePasswordAuthenticationFilter a;
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("angel")
                .password("12#4")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("fide")
                .password("$2a$12$EM0Gw6nTZ2JHZHv5nhOvyOKJEOuneBcYOdXiA/jGxr/57e.xN0H.u")//1562
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}

package com.lukaszplawiak.projectapp.security;

import com.auth0.jwt.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationSuccessHandler successHandler;
    private final RestAuthenticationFailureHandler failureHandler;
    private final String secret;
    private final JWTVerifier verifier;

    public SecurityConfigAdapter(CustomUserDetailsService customUserDetailsService,
                                 PasswordEncoder passwordEncoder,
                                 RestAuthenticationSuccessHandler successHandler,
                                 RestAuthenticationFailureHandler failureHandler,
                                 @Value("${jwt.secret}") String secret,
                                 JWTVerifier verifier) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
        this.verifier = verifier;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthorizationFilter filter = new CustomAuthorizationFilter(authenticationManager(), customUserDetailsService, secret, verifier);
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticateFilter())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }


    public CustomAuthenticationFilter customAuthenticateFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return customAuthenticationFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
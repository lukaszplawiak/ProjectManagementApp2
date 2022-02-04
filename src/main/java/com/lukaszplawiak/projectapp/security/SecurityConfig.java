package com.lukaszplawiak.projectapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RestAuthenticationSuccessHandler successHandler;
    private final RestAuthenticationFailureHandler failureHandler;
    private final String secret;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          PasswordEncoder passwordEncoder,
                          RestAuthenticationSuccessHandler successHandler,
                          RestAuthenticationFailureHandler failureHandler,
                          @Value("${jwt.secret}") String secret) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/login", "/api/v1/token/refresh").permitAll()
                .antMatchers("/api/v1/*"  ).hasAnyAuthority("ROLE_SUPER_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/projects/*").hasAnyAuthority("ROLE_USER")
                .antMatchers("/api/v1/users/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/api/v1/reports/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/api/v1/reports/*").hasAnyAuthority("ROLE_MANAGER")
                .antMatchers("/api/v1/projects/*").hasAnyAuthority("ROLE_MANAGER")
                .antMatchers( "/api/v1/projects/{id}/tasks/*").hasAnyAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticateFilter())
                .addFilter(new CustomAuthorizationFilter(authenticationManager(), customUserDetailsService, secret))
                .headers().frameOptions().disable();
    }

    public CustomAuthenticationFilter customAuthenticateFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean()); // super.authenticationManager()
        return customAuthenticationFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws  Exception {
        return super.authenticationManagerBean();
    }
}

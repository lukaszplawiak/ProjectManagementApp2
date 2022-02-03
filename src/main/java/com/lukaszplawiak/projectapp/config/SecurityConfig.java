package com.lukaszplawiak.projectapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

//@Configuration
public class SecurityConfig  { // extends WebSecurityConfigurerAdapter
//    private final DataSource dataSource;
//
//    public SecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withUser("test")
//                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("test"))
//                .roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().antMatchers("/api/v1/login", "/api/v1/token/refresh").permitAll()
//                .antMatchers("/api/v1/*"  ).hasAnyAuthority("ROLE_SUPER_ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/v1/projects/*").hasAnyAuthority("ROLE_USER")
//                .antMatchers("/api/v1/users/*").hasAnyAuthority("ROLE_ADMIN")
//                .antMatchers("/api/v1/reports/*").hasAnyAuthority("ROLE_ADMIN")
//                .antMatchers("/api/v1/reports/*").hasAnyAuthority("ROLE_MANAGER")
//                .antMatchers("/api/v1/projects/*").hasAnyAuthority("ROLE_MANAGER")
//                .antMatchers( "/api/v1/projects/{id}/tasks/*").hasAnyAuthority("ROLE_USER")
//                .anyRequest().authenticated();
//    }
}

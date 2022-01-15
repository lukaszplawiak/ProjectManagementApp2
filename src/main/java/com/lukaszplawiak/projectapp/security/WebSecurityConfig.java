package com.lukaszplawiak.projectapp.security;

import com.lukaszplawiak.projectapp.filter.CustomAuthenticationFilter;
import com.lukaszplawiak.projectapp.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/api/v1/login", "/api/v1/token/refresh").permitAll() // przed "/api/**" moge podac HttpMethod.Get itp
                .antMatchers(HttpMethod.GET, "/api/v1/projects/{id}", "/api/v1/projects/{id}/tasks/**").hasAnyAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PATCH, "/api/v1/projects/{id}/tasks/{id}").hasAnyAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/projects/{id}/tasks/{id}").hasAnyAuthority("ROLE_USER")
                .antMatchers("/api/v1/projects/{id}", "/api/v1/projects/{id}/tasks/**").hasAnyAuthority("ROLE_MANAGER")
                //.antMatchers("/api/v1/projects/{id}/tasks/**").hasAnyAuthority("ROLE_MANAGER") - generowanie raportow !!
                .antMatchers(HttpMethod.GET,"/api/v1/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/projects/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/api/v1/**"  ).hasAnyAuthority("ROLE_SUPER_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws  Exception {
        return super.authenticationManagerBean();
    }

}

package com.lukaszplawiak.projectapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {
    @Bean
    JWTVerifier jwtVerifier(@Value("${jwt.secret}") String secret) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build();
    }
}

package com.lukaszplawiak.projectapp.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

class CustomAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final CustomUserDetailsService userDetailsService;
    private final String secret;
    private final JWTVerifier verifier;

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager,
                                     CustomUserDetailsService userDetailsService,
                                     @Value("${jwt.secret}") String secret,
                                     JWTVerifier verifier) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
        this.verifier = verifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        getAuthentication(request).ifPresentOrElse(auth -> {
            SecurityContextHolder.getContext().setAuthentication(auth);
            filter(request, response, filterChain);
        }, () -> filter(request, response, filterChain));
    }

    private void filter(HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<UsernamePasswordAuthenticationToken> getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            var userName = verifyToken(token);
            return userName.map(name -> {
                UserDetails userDetails = userDetailsService.loadUserByUsername(name);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            });
        }
        return Optional.empty();
    }

    private Optional<String> verifyToken(String token) {
        try {
            return Optional.ofNullable(verifier.verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
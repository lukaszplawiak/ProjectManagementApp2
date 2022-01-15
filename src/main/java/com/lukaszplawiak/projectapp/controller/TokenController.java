package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.service.RefreshTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
class TokenController {
    private final RefreshTokenService refreshTokenService;

    public TokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping(path = "/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        refreshTokenService.refreshToken(request, response);
    }
}

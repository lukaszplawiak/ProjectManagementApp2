package com.lukaszplawiak.projectapp.controller;

import com.lukaszplawiak.projectapp.service.RefreshTokenService;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
class TokenController {
    private final RefreshTokenService refreshTokenService;

    public TokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping(path = "/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response, @Valid Object req, BindingResult result) throws IOException {
        refreshTokenService.refreshToken(request, response);
    }
}

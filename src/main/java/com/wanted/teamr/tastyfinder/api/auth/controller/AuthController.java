package com.wanted.teamr.tastyfinder.api.auth.controller;

import com.wanted.teamr.tastyfinder.api.auth.dto.TokenCreateRequest;
import com.wanted.teamr.tastyfinder.api.auth.dto.TokenCreateResponse;
import com.wanted.teamr.tastyfinder.api.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenCreateResponse> login(@RequestBody @Valid TokenCreateRequest tokenCreateRequest) {
        TokenCreateResponse tokenCreateResponse = authService.login(tokenCreateRequest);
        return ResponseEntity.ok().body(tokenCreateResponse);
    }
}

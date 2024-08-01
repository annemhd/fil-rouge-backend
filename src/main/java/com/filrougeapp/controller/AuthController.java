package com.filrougeapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.filrougeapp.model.AuthResponse;
import com.filrougeapp.model.User;
import com.filrougeapp.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User request) {

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User request) {

        return ResponseEntity.ok(authService.authenticate(request));
    
package com.filrougeapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.filrougeapp.model.AuthResponse;
import com.filrougeapp.model.User;
import com.filrougeapp.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController  // indique que cette classe est un contrôleur REST, gérant des requêtes HTTP
public class AuthController {

    private final AuthService authService;  // déclaration du service d'authentification

    // constructeur pour injecter le service d'authentification dans le contrôleur
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // méthode pour gérer les requêtes POST à l'URL "/register" pour l'inscription des utilisateurs
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // méthode pour gérer les requêtes POST à l'URL "/login" pour l'authentification des utilisateurs
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
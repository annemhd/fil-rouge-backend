package com.filrougeapp.model;

// classe représentant la réponse d'authentification contenant un token JWT
public class AuthResponse {

    private String token;  // variable pour stocker le token JWT

    // constructeur pour initialiser la réponse d'authentification avec un token
    public AuthResponse(String token) {
        this.token = token;
    }

    // méthode pour obtenir le token JWT
    public String getToken() {
        return token;
    }
}
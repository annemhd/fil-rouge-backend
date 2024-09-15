package com.filrougeapp.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.filrougeapp.service.JwtService;
import com.filrougeapp.service.UserDetailsImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component  // indique que cette classe est un composant Spring, ce qui permet à Spring de la gérer et de l'injecter automatiquement
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // service pour gérer la création, la validation et l'extraction des informations du JWT
    private final UserDetailsImp userDetailsService;  // service pour charger les détails d'utilisateur à partir du nom d'utilisateur

    // constructeur pour initialiser les services nécessaires
    public JwtAuthFilter(JwtService jwtService, UserDetailsImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // récupère l'en-tête d'autorisation de la requête
        String authHeader = request.getHeader("Authorization");

        // vérifie si l'en-tête d'autorisation est présent et commence par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // extrait le token JWT de l'en-tête d'autorisation
        String token = authHeader.substring(7);

        // extrait le nom d'utilisateur du token JWT
        String username = jwtService.extractUsername(token);

        // vérifie si le nom d'utilisateur est non nul et si aucune authentification n'est encore présente dans le contexte de sécurité
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // vérifie si le token JWT est valide
            if (jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // passe à la chaîne de filtres suivante après avoir traité l'authentification
        filterChain.doFilter(request, response);
    }
}
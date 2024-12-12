package com.arnaud.ia.api.config.jwt;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Génère une clé secrète pour signer le token
    private final long expirationMs = 3600000; // Durée de validité : 1 heure

    // Générer un token JWT
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Ajouter le sujet (utilisateur)
                .claim("Administrateur", role) // Ajouter le rôle en tant que claim
                .setIssuedAt(new Date()) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // Date d'expiration
                .signWith(key) // Signer le token avec la clé secrète
                .compact(); // Créer le token compacté
    }

    // Valider un token et extraire les claims
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si un token est expiré
    public boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
}

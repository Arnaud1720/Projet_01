package com.arnaud.ia.api.controller;

import com.arnaud.ia.api.config.jwt.JwtUtil;
import com.arnaud.ia.api.dto.LoginRequest;
import com.arnaud.ia.api.entity.Utilisateurs;
import com.arnaud.ia.api.service.UtilisateursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UtilisateursService utilisateursService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UtilisateursService utilisateursService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.utilisateursService = utilisateursService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Email reçu : " + loginRequest.getEmail());
        System.out.println("Mot de passe reçu : " + loginRequest.getPassword());

        try {
            // Étape 2 : Récupérer l'utilisateur depuis la base de données
            Utilisateurs utilisateur = utilisateursService.findByEmail(loginRequest.getEmail());
            System.out.println("Utilisateur trouvé : " + utilisateur);

            if (utilisateur == null) {
                System.out.println("Utilisateur non trouvé en base de données.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
            }

            // Étape 3 : Vérifier le mot de passe
            if (!passwordEncoder.matches(loginRequest.getPassword(), utilisateur.getPassword())) {
                System.out.println("Mot de passe incorrect pour l'email : " + loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
            }

            // Étape 4 : Générer le token JWT
            String token = jwtUtil.generateToken(utilisateur.getEmail(), utilisateur.getRole().toString());
            System.out.println("Token généré : " + token);

            // Retourner le token dans la réponse
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue");
        }
    }

}

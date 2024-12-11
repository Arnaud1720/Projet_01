package com.arnaud.ia.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'adresse email est invalide")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "L'adresse email est invalide")
    private String email;
    private String password;
    private String telephone;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "adresse_id", referencedColumnName = "id")
    private Addresse adresse;
    @Enumerated(EnumType.STRING) // Indique que l'Enum sera stocké sous forme de texte
    private Roles role; // Nouveau champ pour les rôles
}

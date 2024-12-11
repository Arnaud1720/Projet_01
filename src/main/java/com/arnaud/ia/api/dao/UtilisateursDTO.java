package com.arnaud.ia.api.dao;

import com.arnaud.ia.api.entity.Addresse;
import com.arnaud.ia.api.entity.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilisateursDTO {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    private AddresseDTO adresse; // Ajoutez ceci pour inclure l'adresse
}
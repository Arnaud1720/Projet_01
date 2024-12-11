package com.arnaud.ia.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frigo {
    @Id
    private Integer idFrigo;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    private String contenue;

    @NotNull(message = "La contenance maximale ne peut pas être nulle")
    private Integer contenanceMax;

    @NotNull(message = "La contenance minimale ne peut pas être nulle")
    private Integer contenanceMin;

    private Integer humidite; // Correction orthographique : "Humidité"
    private Integer temperatureActuelle;
    private Integer temperatureCritique;
}

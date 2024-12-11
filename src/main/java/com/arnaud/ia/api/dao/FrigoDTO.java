    package com.arnaud.ia.api.dao;

    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter
    public class FrigoDTO {
        private int idFrigo;
        private String contenue;
        private Integer contenanceMax;
        private Integer contenanceMin;
        private Integer humidite;
        private Integer temperatureActuelle;
        private Integer temperatureCritique;
    }

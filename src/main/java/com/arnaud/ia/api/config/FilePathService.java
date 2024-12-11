package com.arnaud.ia.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePathService {


    @Value("${app.base-path}")
    private String basePath;

    private static final String FIXED_PART = "/api/src/file/";

    public String getFilePath() {
        return basePath + FIXED_PART;
    }

    public void printPath() {
        System.out.println("Le chemin complet pour le fichier : " + getFilePath());
    }
}

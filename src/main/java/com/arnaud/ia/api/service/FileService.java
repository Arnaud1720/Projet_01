package com.arnaud.ia.api.service;

import com.arnaud.ia.api.config.FilePathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FilePathService filePathService;

    public void saveFile(byte[] fileData, String fileName) throws IOException {
        // Obtenir le chemin complet
        String fullPath = filePathService.getFilePath();

        // Créer le dossier s'il n'existe pas
        Path directoryPath = Path.of(fullPath);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Enregistrer le fichier
        Path filePath = Path.of(fullPath, fileName);
        Files.write(filePath, fileData);

        System.out.println("Fichier enregistré à : " + filePath);
    }
}

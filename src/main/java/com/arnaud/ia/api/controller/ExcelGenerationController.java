package com.arnaud.ia.api.controller;

import com.arnaud.ia.api.service.ExcelGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExcelGenerationController {
    private final ExcelGenerationService excelGenerationService;

    @GetMapping("/generate-excel")
    public ResponseEntity<String> generateExcel() {
        String directoryPath = "C:\\PROJET\\api\\src\\file";
        String fileName = "testdufrigo.xlsx";

        try {
            File generatedFile = excelGenerationService.generateExcelFile(directoryPath, fileName);
            return ResponseEntity.ok("Fichier généré avec succès : " + generatedFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la génération du fichier Excel.");
        }
    }
}

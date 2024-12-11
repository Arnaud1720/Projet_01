package com.arnaud.ia.api.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExcelGenerationService {
    public File generateExcelFile(String directoryPath, String fileName) throws IOException {
        // Créer le répertoire s’il n’existe pas
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        // Créer le fichier .xlsx
        Path filePath = directory.resolve(fileName);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Frigos");

            // Exemple de ligne d’en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("idFrigo");
            headerRow.createCell(1).setCellValue("contenue");
            headerRow.createCell(2).setCellValue("contenanceMax");
            headerRow.createCell(3).setCellValue("contenanceMin");
            headerRow.createCell(4).setCellValue("humidite");
            headerRow.createCell(5).setCellValue("temperatureActuelle");
            headerRow.createCell(6).setCellValue("temperatureCritique");

            // Ajout d'une ligne de données par exemple
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(1);
            dataRow.createCell(1).setCellValue("Liquide");
            dataRow.createCell(2).setCellValue(2300);
            dataRow.createCell(3).setCellValue(200);
            dataRow.createCell(4).setCellValue(50);
            dataRow.createCell(5).setCellValue(2);
            dataRow.createCell(6).setCellValue(12);

            // Vous pouvez ajuster la taille des colonnes
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            // Écrire le fichier sur le disque
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                workbook.write(fos);
            }
        }

        return filePath.toFile();
    }
}

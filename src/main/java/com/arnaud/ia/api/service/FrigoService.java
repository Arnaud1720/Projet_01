package com.arnaud.ia.api.service;

import com.arnaud.ia.api.dao.FrigoDTO;
import com.arnaud.ia.api.entity.Frigo;
import com.arnaud.ia.api.mapper.FrigoMapper;
import com.arnaud.ia.api.repository.FrigoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FrigoService {
    private final FrigoRepository frigoRepository;
    private final FrigoMapper frigoMapper;

    public List<FrigoDTO> saveFromExcel(MultipartFile file) throws Exception {
        List<Frigo> frigosFromExcel = new ArrayList<>();

        // Lire les données depuis le fichier Excel
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Ignorer la ligne d'en-tête

                Frigo frigo = new Frigo();
                frigo.setIdFrigo((int) row.getCell(0).getNumericCellValue()); // ID unique
                frigo.setContenue(row.getCell(1).getStringCellValue());
                frigo.setContenanceMax((int) row.getCell(2).getNumericCellValue());
                frigo.setContenanceMin((int) row.getCell(3).getNumericCellValue());
                frigo.setHumidite((int) row.getCell(4).getNumericCellValue());
                frigo.setTemperatureActuelle((int) row.getCell(5).getNumericCellValue());
                frigo.setTemperatureCritique((int) row.getCell(6).getNumericCellValue());

                frigosFromExcel.add(frigo);
            }
        }

        // Récupérer tous les Frigos existants depuis la base
        List<Frigo> frigosFromDatabase = frigoRepository.findAll();

        // Construire une map pour un accès rapide aux Frigos existants par ID
        Map<Integer, Frigo> frigosFromDatabaseMap = frigosFromDatabase.stream()
                .collect(Collectors.toMap(Frigo::getIdFrigo, frigo -> frigo));

        // Parcourir les Frigos du fichier Excel et synchroniser avec la base
        List<Frigo> frigosToSave = new ArrayList<>();
        for (Frigo frigoFromExcel : frigosFromExcel) {
            if (frigosFromDatabaseMap.containsKey(frigoFromExcel.getIdFrigo())) {
                // Si l'entité existe, mettre à jour ses valeurs
                Frigo existingFrigo = frigosFromDatabaseMap.get(frigoFromExcel.getIdFrigo());
                existingFrigo.setContenue(frigoFromExcel.getContenue());
                existingFrigo.setContenanceMax(frigoFromExcel.getContenanceMax());
                existingFrigo.setContenanceMin(frigoFromExcel.getContenanceMin());
                existingFrigo.setHumidite(frigoFromExcel.getHumidite());
                existingFrigo.setTemperatureActuelle(frigoFromExcel.getTemperatureActuelle());
                existingFrigo.setTemperatureCritique(frigoFromExcel.getTemperatureCritique());
                frigosToSave.add(existingFrigo);
            } else {
                // Sinon, ajouter comme nouvel enregistrement
                frigosToSave.add(frigoFromExcel);
            }
        }

        // Supprimer les Frigos absents du fichier Excel
        Set<Integer> excelIds = frigosFromExcel.stream()
                .map(Frigo::getIdFrigo)
                .collect(Collectors.toSet());

        List<Frigo> frigosToDelete = frigosFromDatabase.stream()
                .filter(frigo -> !excelIds.contains(frigo.getIdFrigo()))
                .collect(Collectors.toList());

        frigoRepository.deleteAll(frigosToDelete);

        // Sauvegarder les Frigos synchronisés
        List<Frigo> savedFrigos = frigoRepository.saveAll(frigosToSave);

        // Convertir les entités sauvegardées en DTO
        return savedFrigos.stream()
                .map(frigoMapper::toDto)
                .toList();
    }
}

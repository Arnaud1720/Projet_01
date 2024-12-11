package com.arnaud.ia.api.controller;

import com.arnaud.ia.api.dao.FrigoDTO;
import com.arnaud.ia.api.service.FrigoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/frigos")
@RequiredArgsConstructor
public class FrigoController {
    private final FrigoService frigoService;

    @ApiOperation(value = "Importer un fichier Excel", notes = "Téléversez un fichier Excel contenant les données des frigos.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FrigoDTO>> uploadFrigoData(@RequestParam("file") MultipartFile file) {
        System.out.println("Méthode uploadFrigoData appelée.");
        try {
            if (file == null || file.isEmpty()) {
                System.out.println("Le fichier est vide ou non fourni !");
                throw new IllegalArgumentException("Le fichier est vide !");
            }
            System.out.println("Nom du fichier : " + file.getOriginalFilename());
            System.out.println("Type de contenu : " + file.getContentType());
            System.out.println("Taille du fichier : " + file.getSize() + " octets");
            List<FrigoDTO> frigos = frigoService.saveFromExcel(file);
            return ResponseEntity.ok(frigos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }



}

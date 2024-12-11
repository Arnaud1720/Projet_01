package com.arnaud.ia.api.controller;

import com.arnaud.ia.api.dao.UtilisateursDTO;
import com.arnaud.ia.api.service.UtilisateursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {
    private final UtilisateursService utilisateursService;

    @PostMapping
    public ResponseEntity<UtilisateursDTO> createUtilisateur(@RequestBody @Valid UtilisateursDTO utilisateursDTO) {
        try {
            UtilisateursDTO createdUtilisateur = utilisateursService.createUtilisateur(utilisateursDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUtilisateur);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateursDTO> getUtilisateurById(@PathVariable int id) {
        try {
            UtilisateursDTO findbyId = utilisateursService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(findbyId);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UtilisateursDTO> deleteUtilisateurById(@PathVariable int id) {
        try {
            UtilisateursDTO deleteByID = utilisateursService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(deleteByID);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

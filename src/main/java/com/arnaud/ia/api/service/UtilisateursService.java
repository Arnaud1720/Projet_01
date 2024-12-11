package com.arnaud.ia.api.service;

import com.arnaud.ia.api.dao.UtilisateursDTO;
import com.arnaud.ia.api.entity.Addresse;
import com.arnaud.ia.api.entity.Roles;
import com.arnaud.ia.api.entity.Utilisateurs;
import com.arnaud.ia.api.mapper.AddresseMapper;
import com.arnaud.ia.api.mapper.UtilisateursMapper;
import com.arnaud.ia.api.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateursService {
    private final UtilisateurRepository utilisateursRepository;
    private final UtilisateursMapper utilisateursMapper;
    private final AddresseMapper addresseMapper; // Créez un mapper pour convertir AddresseDTO <-> Addresse

    public UtilisateursDTO createUtilisateur(UtilisateursDTO utilisateursDTO) throws Exception {
        // Mapper le DTO utilisateur en entité utilisateur
        Utilisateurs utilisateurs = utilisateursMapper.toEntity(utilisateursDTO);

        // Associer l'adresse (qui est déjà incluse dans le DTO)
        if (utilisateursDTO.getAdresse() != null) {
            Addresse addresse = addresseMapper.toEntity(utilisateursDTO.getAdresse());
            utilisateurs.setAdresse(addresse);
        }
        if(!utilisateurs.getNom().startsWith("adm_")){
            utilisateurs.setRole(Roles.Utilisateur);
        }else if (utilisateurs.getNom().startsWith("adm_")){
            utilisateurs.setRole(Roles.Administrateur);
        }
        if(utilisateursRepository.existsByEmail(utilisateurs.getEmail())){
            throw new Exception("un utilisateur existe dèjà avec cette email");
        }

        // Sauvegarder l'utilisateur (l'adresse sera sauvegardée automatiquement si la relation est configurée avec CascadeType.ALL)
        utilisateursRepository.save(utilisateurs);

        // Retourner le DTO utilisateur
        return utilisateursMapper.toDto(utilisateurs);

    }
    public UtilisateursDTO findById(int id) {
        Utilisateurs utilisateurs = utilisateursRepository.findById(id).orElse(null);
        if (utilisateurs != null) {
            System.out.println("Adresse associée : " + utilisateurs.getAdresse());
        }
        return utilisateursMapper.toDto(utilisateurs);
    }

    public UtilisateursDTO deleteById(int id) {
        utilisateursRepository.deleteById(id);
        return null;
    }

}

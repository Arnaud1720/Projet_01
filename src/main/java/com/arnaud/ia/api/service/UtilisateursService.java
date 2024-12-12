package com.arnaud.ia.api.service;

import com.arnaud.ia.api.dao.UtilisateursDTO;
import com.arnaud.ia.api.entity.Addresse;
import com.arnaud.ia.api.entity.Roles;
import com.arnaud.ia.api.entity.Utilisateurs;
import com.arnaud.ia.api.exception.EmailAlreadyExistsException;
import com.arnaud.ia.api.exception.UserNotFoundException;
import com.arnaud.ia.api.mapper.AddresseMapper;
import com.arnaud.ia.api.mapper.UtilisateursMapper;
import com.arnaud.ia.api.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilisateursService {
    private final UtilisateurRepository utilisateursRepository;
    private final UtilisateursMapper utilisateursMapper;
    private final AddresseMapper addresseMapper;
    private final PasswordEncoder passwordEncoder;

    public UtilisateursDTO createUtilisateur(UtilisateursDTO utilisateursDTO) throws Exception {
        // Mapper le DTO utilisateur en entité utilisateur
        Utilisateurs utilisateurs = utilisateursMapper.toEntity(utilisateursDTO);
        log.info("Création de l'utilisateur avec email : {}", utilisateursDTO.getEmail());

        // Associer l'adresse (qui est déjà incluse dans le DTO)
        if (utilisateursDTO.getAdresse() != null) {
            Addresse addresse = addresseMapper.toEntity(utilisateursDTO.getAdresse());
            utilisateurs.setAdresse(addresse);
        }
        if(utilisateurs.getNom().startsWith("adm_")){
            utilisateurs.setRole(Roles.Administrateur);
        }else {
            utilisateurs.setRole(Roles.Utilisateur);
        }
        if(utilisateursRepository.existsByEmail(utilisateurs.getEmail())){
            throw new EmailAlreadyExistsException("un utilisateur existe dèjà avec cette email");
        }

        // Chiffrer le mot de passe
        utilisateurs.setPassword(passwordEncoder.encode(utilisateursDTO.getPassword()));

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
    public Utilisateurs findByEmail(String email) {
        return utilisateursRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }
}

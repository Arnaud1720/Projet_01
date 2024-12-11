package com.arnaud.ia.api.mapper;

import com.arnaud.ia.api.dao.UtilisateursDTO;
import com.arnaud.ia.api.entity.Utilisateurs;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface  UtilisateursMapper {

    UtilisateursDTO toDto(Utilisateurs utilisateurs);
    Utilisateurs toEntity(UtilisateursDTO utilisateursDTO);
}

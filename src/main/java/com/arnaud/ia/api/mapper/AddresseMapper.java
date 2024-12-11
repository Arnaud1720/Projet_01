package com.arnaud.ia.api.mapper;

import com.arnaud.ia.api.dao.AddresseDTO;
import com.arnaud.ia.api.dao.UtilisateursDTO;
import com.arnaud.ia.api.entity.Addresse;
import com.arnaud.ia.api.entity.Utilisateurs;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddresseMapper {

    Addresse toEntity(AddresseDTO dto);
    AddresseDTO toDto(Addresse entity);
}

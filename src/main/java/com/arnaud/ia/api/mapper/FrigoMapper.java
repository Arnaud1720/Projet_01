package com.arnaud.ia.api.mapper;

import com.arnaud.ia.api.dao.FrigoDTO;
import com.arnaud.ia.api.dao.UtilisateursDTO;
import com.arnaud.ia.api.entity.Frigo;
import com.arnaud.ia.api.entity.Utilisateurs;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FrigoMapper {
    FrigoDTO toDto(Frigo frigo);
    Frigo toEntity(FrigoDTO frigoDTO);
}

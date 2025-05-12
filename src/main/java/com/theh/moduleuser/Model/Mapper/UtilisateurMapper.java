package com.theh.moduleuser.Model.Mapper;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Model.Utilisateur;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "default")
public interface UtilisateurMapper {
    UtilisateurDto toDto(Utilisateur entity);

    Utilisateur toEntity(UtilisateurDto dto);

    List<UtilisateurDto> toDtoList(List<Utilisateur> entities);

    List<Utilisateur> toEntityList(List<UtilisateurDto> dtos);
}

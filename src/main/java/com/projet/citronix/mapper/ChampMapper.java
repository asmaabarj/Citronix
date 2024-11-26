package com.projet.citronix.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.projet.citronix.dtos.ChampDTO;
import com.projet.citronix.models.entities.Champ;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FermeMapper.class})
public interface ChampMapper {

    @Mapping(source = "ferme.id", target = "fermeId")
    ChampDTO toDto(Champ champ);

    @Mapping(source = "fermeId", target = "ferme.id")
    Champ toEntity(ChampDTO champDTO);

    List<ChampDTO> toDtoList(List<Champ> champs);

    List<Champ> toEntityList(List<ChampDTO> champDTOs);
}
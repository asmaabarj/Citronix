package com.projet.citronix.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.projet.citronix.dtos.FermeDTO;
import com.projet.citronix.models.entities.Ferme;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FermeMapper {
    FermeDTO toDto(Ferme ferme);
    Ferme toEntity(FermeDTO fermeDTO);
    
    List<FermeDTO> toDtoList(List<Ferme> fermes);
    
    List<Ferme> toEntityList(List<FermeDTO> fermeDTOs);
}

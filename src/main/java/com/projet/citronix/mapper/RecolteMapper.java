package com.projet.citronix.mapper;

import com.projet.citronix.dtos.RecolteDTO;
import com.projet.citronix.models.entities.Recolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetailRecolteMapper.class})
public interface RecolteMapper {
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "ventes", ignore = true)
    Recolte toEntity(RecolteDTO dto);

    @Mapping(target = "details", ignore = true)
    RecolteDTO toDto(Recolte entity);

    List<RecolteDTO> toDtoList(List<Recolte> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "ventes", ignore = true)
    void updateEntity(RecolteDTO dto, @MappingTarget Recolte entity);
} 
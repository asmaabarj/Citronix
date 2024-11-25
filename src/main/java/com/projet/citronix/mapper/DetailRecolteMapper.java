package com.projet.citronix.mapper;

import com.projet.citronix.dtos.DetailRecolteDTO;
import com.projet.citronix.models.entities.DetailRecolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ArbreMapper.class})
public interface DetailRecolteMapper {
    @Mapping(target = "arbreId", source = "arbre.id")
    @Mapping(target = "recolteId", source = "recolte.id")
    DetailRecolteDTO toDto(DetailRecolte entity);

    @Mapping(target = "arbre", ignore = true)
    @Mapping(target = "recolte", ignore = true)
    DetailRecolte toEntity(DetailRecolteDTO dto);

    List<DetailRecolteDTO> toDtoList(List<DetailRecolte> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arbre", ignore = true)
    @Mapping(target = "recolte", ignore = true)
    void updateEntity(DetailRecolteDTO dto, @MappingTarget DetailRecolte entity);
}
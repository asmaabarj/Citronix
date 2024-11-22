package com.projet.citronix.mapper;

import com.projet.citronix.dtos.VenteDTO;
import com.projet.citronix.models.entities.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenteMapper {
    @Mapping(target = "recolteId", source = "recolte.id")
    @Mapping(target = "revenu", expression = "java(entity.calculerRevenu())")
    VenteDTO toDto(Vente entity);

    @Mapping(target = "recolte", ignore = true)
    Vente toEntity(VenteDTO dto);

    List<VenteDTO> toDtoList(List<Vente> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recolte", ignore = true)
    void updateEntity(VenteDTO dto, @MappingTarget Vente entity);
}

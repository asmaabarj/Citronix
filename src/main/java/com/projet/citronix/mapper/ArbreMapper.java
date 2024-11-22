package com.projet.citronix.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.projet.citronix.dtos.ArbreDTO;
import com.projet.citronix.models.entities.Arbre;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArbreMapper {
    @Mapping(target = "age", expression = "java(arbre.calculerAge())")
    @Mapping(target = "productiviteAnnuelle", expression = "java(arbre.calculerProductivite())")
    @Mapping(target = "categorieArbre", expression = "java(arbre.determinerCategorie())")
    @Mapping(source = "champ.id", target = "champId")
    ArbreDTO toDTO(Arbre arbre);

    @Mapping(target = "age", ignore = true)
    @Mapping(target = "productiviteAnnuelle", ignore = true)
    @Mapping(target = "categorieArbre", ignore = true)
    @Mapping(source = "champId", target = "champ.id")
    Arbre toEntity(ArbreDTO arbreDTO);

    List<ArbreDTO> toDTOList(List<Arbre> arbres);
}

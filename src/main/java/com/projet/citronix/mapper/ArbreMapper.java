package com.projet.citronix.mapper;

import com.projet.citronix.dtos.ArbreDTO;
import com.projet.citronix.models.entities.Arbre;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArbreMapper {
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "productiviteAnnuelle", ignore = true)
    @Mapping(target = "categorieArbre", ignore = true)
    @Mapping(source = "champId", target = "champ.id")
    Arbre toEntity(ArbreDTO arbreDTO);

    @Mapping(source = "champ.id", target = "champId")
    ArbreDTO toDTO(Arbre arbre);

    List<ArbreDTO> toDTOList(List<Arbre> arbres);

    @AfterMapping
    default void afterToDTO(Arbre arbre, @MappingTarget ArbreDTO arbreDTO) {
        arbreDTO.setAge(arbre.calculerAge());
        arbreDTO.setProductiviteAnnuelle(arbre.calculerProductivite());
        arbreDTO.setCategorieArbre(arbre.determinerCategorie());
    }
}

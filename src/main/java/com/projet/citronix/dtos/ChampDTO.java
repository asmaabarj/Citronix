package com.projet.citronix.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampDTO {
    private Long id;

    @NotBlank(message = "Le nom du champ est obligatoire")
    private String nom;

    @NotNull(message = "La superficie est obligatoire")
    @DecimalMin(value = "0.1", message = "La superficie doit être supérieure ou égale à 0.1 hectare")
    private Double superficie;

    @NotNull(message = "L'ID de la ferme est obligatoire")
    private Long fermeId;
}

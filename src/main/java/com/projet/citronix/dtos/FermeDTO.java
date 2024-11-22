package com.projet.citronix.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeDTO {
    private Long id;

    @NotBlank(message = "Le nom de la ferme est obligatoire")
    private String nom;

    @NotBlank(message = "La localisation est obligatoire")
    private String localisation;

    @NotNull(message = "La superficie est obligatoire")
    @DecimalMin(value = "0.2", message = "La superficie doit être supérieure ou égale à 0.2 hectares")
    private Double superficie;

    @NotNull(message = "La date de création est obligatoire")
    private Date dateCreation;
}

package com.projet.citronix.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenteDTO {
    private Long id;

    @NotNull(message = "La date de vente est obligatoire")
    private Date dateVente;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @Positive(message = "Le prix unitaire doit être positif")
    private Double prixUnitaire;

    @NotBlank(message = "Le client est obligatoire")
    private String client;

    @NotNull(message = "L'ID de la récolte est obligatoire")
    private Long recolteId;

    private Double revenu;
}

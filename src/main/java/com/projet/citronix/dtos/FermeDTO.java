package com.projet.citronix.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Positive(message = "La superficie doit être positive")
    private Double superficie;
    
    @NotNull(message = "La date de création est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;
}

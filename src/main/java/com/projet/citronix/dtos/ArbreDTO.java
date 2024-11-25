package com.projet.citronix.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArbreDTO {
    private Long id;
    
    @NotNull(message = "La date de plantation est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date datePlantation;
    
    @NotNull(message = "L'ID du champ est obligatoire")
    private Long champId;
    
    private Integer age;
    private Float productiviteAnnuelle;
    private String categorieArbre;
}

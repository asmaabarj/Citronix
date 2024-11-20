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
public class RecolteDTO {
    private Long id;
    
    @NotNull(message = "La date de récolte est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateRecolte;
    
    @NotNull(message = "La saison est obligatoire")
    private Saison saison;
}

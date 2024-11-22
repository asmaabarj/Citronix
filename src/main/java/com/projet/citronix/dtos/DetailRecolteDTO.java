package com.projet.citronix.dtos;

import lombok.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailRecolteDTO {
    private Long id;
    private Float quantiteParArbre;
    
    @NotNull(message = "L'ID de l'arbre est obligatoire")
    private Long arbreId;
    
    @NotNull(message = "L'ID de la récolte est obligatoire")
    private Long recolteId;
} 
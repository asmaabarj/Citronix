package com.projet.citronix.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampDTO {
    private Long id;
    
    @NotNull(message = "La superficie est obligatoire")
    @Min(value = 1000, message = "La superficie minimale est de 1000 m²")
    private Double superficie;
    
    @NotNull(message = "L'ID de la ferme est obligatoire")
    private Long fermeId;
}

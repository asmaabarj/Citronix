package com.projet.citronix.dtos;

import com.projet.citronix.models.enums.Saison;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecolteDTO {
    private Long id;

    @NotNull(message = "La date de récolte est obligatoire")
    private Date dateRecolte;

    @NotNull(message = "La quantité totale est obligatoire")
    @Positive(message = "La quantité totale doit être positive")
    private Double quantiteTotale;

    @NotNull(message = "La saison est obligatoire")
    private Saison saison;

    private List<DetailRecolteDTO> details;
}

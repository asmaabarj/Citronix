package com.projet.citronix.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projet.citronix.models.enums.Saison;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecolteDTO {
    private Long id;

    @NotNull(message = "La date de récolte est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateRecolte;

    private Double quantiteTotale;

    @NotNull(message = "La saison est obligatoire")
    private Saison saison;

    @Builder.Default
    private List<DetailRecolteDTO> details = new ArrayList<>();
}

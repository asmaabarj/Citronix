package com.projet.citronix.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.projet.citronix.models.enums.Saison;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de récolte est obligatoire")
    @Temporal(TemporalType.DATE)
    private Date dateRecolte;

    private Double quantiteTotale;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La saison est obligatoire")
    private Saison saison;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    @Builder.Default
    private List<DetailRecolte> details = new ArrayList<>();

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Vente> ventes = new ArrayList<>();
}

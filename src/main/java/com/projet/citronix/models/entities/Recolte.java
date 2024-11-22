package com.projet.citronix.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.projet.citronix.models.enums.Saison;

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

    @NotNull(message = "La quantité totale est obligatoire")
    @Positive(message = "La quantité totale doit être positive")
    private Double quantiteTotale;

    @NotNull(message = "La saison est obligatoire")
    @Enumerated(EnumType.STRING)
    private Saison saison;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetailRecolte> details = new ArrayList<>();

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Vente> ventes = new ArrayList<>();
}

package com.projet.citronix.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailRecolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La quantité par arbre est obligatoire")
    @Positive(message = "La quantité par arbre doit être positive")
    private Double quantiteParArbre;

    @ManyToOne
    @JoinColumn(name = "arbre_id", nullable = false)
    private Arbre arbre;

    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;
}

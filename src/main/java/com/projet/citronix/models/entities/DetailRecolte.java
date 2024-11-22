package com.projet.citronix.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailRecolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float quantiteParArbre;

    @ManyToOne
    @JoinColumn(name = "arbre_id", nullable = false)
    private Arbre arbre;

    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;

    @PrePersist
    @PreUpdate
    private void calculerQuantiteParArbre() {
        if (arbre != null) {
            this.quantiteParArbre = arbre.calculerProductivite();
        }
    }
}


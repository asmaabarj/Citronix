package com.projet.citronix.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Champ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du champ est obligatoire")
    private String nom;

    @NotNull(message = "La superficie est obligatoire")
    private Double superficie;

    @ManyToOne
    @JoinColumn(name = "ferme_id", nullable = false)
    private Ferme ferme;

    @OneToMany(mappedBy = "champ", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Arbre> arbres = new ArrayList<>();
}

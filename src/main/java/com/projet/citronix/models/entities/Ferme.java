package com.projet.citronix.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Ferme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la ferme est obligatoire")
    private String nom;

    @NotBlank(message = "La localisation est obligatoire")
    private String localisation;

    @NotNull(message = "La superficie est obligatoire")
    private Double superficie;

    @NotNull(message = "La date de création est obligatoire")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @OneToMany(mappedBy = "ferme", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Champ> champs = new ArrayList<>();
}

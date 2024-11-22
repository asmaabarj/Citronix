package com.projet.citronix.models.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "La date de vente est obligatoire")
    @Temporal(TemporalType.DATE)
    private Date dateVente;
    
    @NotNull(message = "Le prix unitaire est obligatoire")
    @Positive(message = "Le prix unitaire doit être positif")
    private Double prixUnitaire;
    
    @NotBlank(message = "Le client est obligatoire")
    private String client;
    
    @ManyToOne
    @JoinColumn(name = "recolte_id", nullable = false)
    private Recolte recolte;
}

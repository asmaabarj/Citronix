package com.projet.citronix.models.entities;

import com.projet.citronix.models.enums.Saison;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

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
    @PositiveOrZero(message = "La quantité totale doit être positive ou nulle")
    private Double quantiteTotale;
    
    @Enumerated(EnumType.STRING)
    private Saison saison;
    
    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<DetailRecolte> details = new ArrayList<>();
    
    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<Vente> ventes = new ArrayList<>();
}

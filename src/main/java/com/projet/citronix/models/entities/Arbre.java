package com.projet.citronix.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "La date de plantation est obligatoire")
    @Temporal(TemporalType.DATE)
    private Date datePlantation;
    
    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;
    
    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL)
    private List<DetailRecolte> detailsRecoltes = new ArrayList<>();
    
    private Boolean estProductif;
}

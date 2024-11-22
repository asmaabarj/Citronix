package com.projet.citronix.models.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.projet.citronix.exceptions.ArbreException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.ValidationException;
import javax.validation.constraints.Past;

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
    @Past(message = "La date de plantation doit etre en passé")
    private Date datePlantation;
    
    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;
    
    @OneToMany(mappedBy = "arbre", cascade = CascadeType.ALL)
    private List<DetailRecolte> detailsRecoltes = new ArrayList<>();

    @Transient
    private Integer age;

    @Transient
    private Float productiviteAnnuelle;

    @Transient
    private String categorieArbre;

    @PrePersist
    @PreUpdate
    private void validateArbre() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePlantation);
        int moisPlantation = cal.get(Calendar.MONTH) + 1;
        
        if (moisPlantation < 3 || moisPlantation > 5) {
            throw new ArbreException.PeriodePlantationInvalideException();
        }
    }

    public Integer calculerAge() {
        if (datePlantation == null) return 0;
        Calendar plantation = Calendar.getInstance();
        plantation.setTime(datePlantation);
        Calendar aujourdhui = Calendar.getInstance();
        return aujourdhui.get(Calendar.YEAR) - plantation.get(Calendar.YEAR);
    }

    public Float calculerProductivite() {
        int age = calculerAge();
        if (age > 20) return 0f;
        if (age > 10) return 20f;
        if (age >= 3) return 12f;
        return 2.5f;
    }

    public String determinerCategorie() {
        int age = calculerAge();
        if (age > 20) return "NON_PRODUCTIF";
        if (age > 10) return "VIEUX";
        if (age >= 3) return "MATURE";
        return "JEUNE";
    }
}

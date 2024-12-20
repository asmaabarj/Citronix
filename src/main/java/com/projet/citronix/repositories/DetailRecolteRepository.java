package com.projet.citronix.repositories;

import com.projet.citronix.models.entities.DetailRecolte;
import com.projet.citronix.models.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRecolteRepository extends JpaRepository<DetailRecolte, Long> {
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DetailRecolte d " +
           "WHERE d.arbre.id = :arbreId " +
           "AND d.recolte.saison = :saison " +
           "AND YEAR(d.recolte.dateRecolte) = :anneeRecolte")
    boolean existsByArbreIdAndRecolteSaisonAndAnneeRecolte(
        Long arbreId, 
        Saison saison, 
        int anneeRecolte
    );
}

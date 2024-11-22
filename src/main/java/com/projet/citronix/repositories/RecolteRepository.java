package com.projet.citronix.repositories;

import com.projet.citronix.models.entities.Recolte;
import com.projet.citronix.models.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecolteRepository extends JpaRepository<Recolte, Long> {
    List<Recolte> findBySaison(Saison saison);
    boolean existsBySaison(Saison saison);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Recolte r " +
           "WHERE r.saison = :saison AND YEAR(r.dateRecolte) = :annee")
    boolean existsBySaisonAndAnnee(Saison saison, int annee);
}

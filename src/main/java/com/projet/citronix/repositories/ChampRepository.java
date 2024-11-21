package com.projet.citronix.repositories;

import com.projet.citronix.models.entities.Champ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampRepository extends JpaRepository<Champ, Long> {
    List<Champ> findByFermeId(Long fermeId);
    
    @Query("SELECT COUNT(c) FROM Champ c WHERE c.ferme.id = :fermeId")
    long countByFermeId(@Param("fermeId") Long fermeId);
    
    @Query("SELECT SUM(c.superficie) FROM Champ c WHERE c.ferme.id = :fermeId")
    Double sumSuperficieByFermeId(@Param("fermeId") Long fermeId);
}

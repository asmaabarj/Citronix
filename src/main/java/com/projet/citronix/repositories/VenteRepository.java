package com.projet.citronix.repositories;

import com.projet.citronix.models.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByRecolteId(Long recolteId);
}

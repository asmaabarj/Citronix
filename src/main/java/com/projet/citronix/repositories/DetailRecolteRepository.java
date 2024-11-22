package com.projet.citronix.repositories;

import com.projet.citronix.models.entities.DetailRecolte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRecolteRepository extends JpaRepository<DetailRecolte, Long> {
    List<DetailRecolte> findByRecolteId(Long recolteId);
    List<DetailRecolte> findByArbreId(Long arbreId);
    boolean existsByArbreIdAndRecolteId(Long arbreId, Long recolteId);
}

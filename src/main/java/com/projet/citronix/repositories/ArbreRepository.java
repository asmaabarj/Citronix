package com.projet.citronix.repositories;

import com.projet.citronix.models.entities.Arbre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArbreRepository extends JpaRepository<Arbre, Long> {
    List<Arbre> findByChampId(Long champId);
    long countByChampId(Long champId);
}

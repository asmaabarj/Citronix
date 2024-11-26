package com.projet.citronix.services.interfaces;

import com.projet.citronix.dtos.RecolteDTO;
import com.projet.citronix.models.enums.Saison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecolteService {
    RecolteDTO creer(RecolteDTO recolteDTO);
    RecolteDTO modifier(Long id, RecolteDTO recolteDTO);
    RecolteDTO recupererParId(Long id);
    void supprimer(Long id);
    List<RecolteDTO> recupererTout();
    List<RecolteDTO> recupererParSaison(Saison saison);
    void mettreAJourQuantiteTotale(Long recolteId);
}

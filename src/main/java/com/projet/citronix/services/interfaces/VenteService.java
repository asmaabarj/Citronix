package com.projet.citronix.services.interfaces;

import com.projet.citronix.dtos.VenteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VenteService {
    VenteDTO creer(VenteDTO venteDTO);
    VenteDTO modifier(Long id, VenteDTO venteDTO);
    VenteDTO recupererParId(Long id);
    void supprimer(Long id);
    List<VenteDTO> recupererTout();
    Page<VenteDTO> rechercher(Pageable pageable);
    List<VenteDTO> recupererParRecolte(Long recolteId);
}

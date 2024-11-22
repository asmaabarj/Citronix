package com.projet.citronix.services.interfaces;

import com.projet.citronix.dtos.DetailRecolteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DetailRecolteService {
    DetailRecolteDTO creer(DetailRecolteDTO detailRecolteDTO);
    DetailRecolteDTO modifier(Long id, DetailRecolteDTO detailRecolteDTO);
    DetailRecolteDTO recupererParId(Long id);
    void supprimer(Long id);
    List<DetailRecolteDTO> recupererTout();
    Page<DetailRecolteDTO> rechercher(Pageable pageable);
    List<DetailRecolteDTO> recupererParRecolte(Long recolteId);
    List<DetailRecolteDTO> recupererParArbre(Long arbreId);
} 
package com.projet.citronix.services.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.projet.citronix.dtos.FermeDTO;

public interface FermeService {
    FermeDTO creer(FermeDTO fermeDTO);
    FermeDTO modifier(Long id, FermeDTO fermeDTO);
    FermeDTO recupererParId(Long id);
    void supprimer(Long id);
    List<FermeDTO> recupererTout();
    List<FermeDTO> rechercher(Map<String, String> criteres);
    List<FermeDTO> recupererTout(Pageable pageable);
}


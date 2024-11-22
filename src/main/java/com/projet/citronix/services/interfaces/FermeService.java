package com.projet.citronix.services.interfaces;

import com.projet.citronix.dtos.FermeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface FermeService {
    FermeDTO creer(FermeDTO fermeDTO);
    FermeDTO modifier(Long id, FermeDTO fermeDTO);
    FermeDTO recupererParId(Long id);
    void supprimer(Long id);
    List<FermeDTO> recupererTout();
    Page<FermeDTO> rechercher(Map<String, String> criteres, Pageable pageable);
}

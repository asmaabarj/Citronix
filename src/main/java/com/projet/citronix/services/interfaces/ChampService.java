package com.projet.citronix.services.interfaces;

import com.projet.citronix.dtos.ChampDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChampService {
    ChampDTO creer(ChampDTO champDTO);
    ChampDTO modifier(Long id, ChampDTO champDTO);
    ChampDTO recupererParId(Long id);
    void supprimer(Long id);
    List<ChampDTO> recupererParFermeId(Long fermeId);
    Page<ChampDTO> recupererTout(Pageable pageable);
}

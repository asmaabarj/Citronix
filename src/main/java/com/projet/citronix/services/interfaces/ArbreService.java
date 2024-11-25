package com.projet.citronix.services.interfaces;

import com.projet.citronix.dtos.ArbreDTO;
import java.util.List;

public interface ArbreService {
    ArbreDTO creerArbre(ArbreDTO arbreDTO);
    ArbreDTO modifierArbre(Long id, ArbreDTO arbreDTO);
    ArbreDTO getArbreById(Long id);
    List<ArbreDTO> getAllArbres();
    void supprimerArbre(Long id);
    List<ArbreDTO> getArbresByChamp(Long champId);
}

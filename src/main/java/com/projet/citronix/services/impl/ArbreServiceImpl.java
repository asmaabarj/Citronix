package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.ArbreDTO;
import com.projet.citronix.exceptions.ArbreException;
import com.projet.citronix.mapper.ArbreMapper;
import com.projet.citronix.models.entities.Arbre;
import com.projet.citronix.models.entities.Champ;
import com.projet.citronix.repositories.ArbreRepository;
import com.projet.citronix.repositories.ChampRepository;
import com.projet.citronix.services.interfaces.ArbreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ArbreServiceImpl implements ArbreService {
    private final ArbreRepository arbreRepository;
    private final ChampRepository champRepository;
    private final ArbreMapper arbreMapper;

    @Override
    public ArbreDTO creerArbre(ArbreDTO arbreDTO) {
        log.info("Début création arbre pour le champ ID: {}", arbreDTO.getChampId());
        
        Champ champ = getChamp(arbreDTO.getChampId());
        validerPeriodePlantation(arbreDTO);
        validerDensiteArbres(champ);

        Arbre arbre = arbreMapper.toEntity(arbreDTO);
        arbre.setChamp(champ);
        
        Arbre savedArbre = arbreRepository.save(arbre);
        log.info("Arbre créé avec succès, ID: {}", savedArbre.getId());
        
        return arbreMapper.toDTO(savedArbre);
    }

    @Override
    public ArbreDTO modifierArbre(Long id, ArbreDTO arbreDTO) {
        log.info("Début modification arbre ID: {}", id);
        
        Arbre arbre = getArbre(id);
        Champ nouveauChamp = getChamp(arbreDTO.getChampId());
        
        if (!arbre.getChamp().getId().equals(arbreDTO.getChampId())) {
            validerDensiteArbres(nouveauChamp);
        }
        
        validerPeriodePlantation(arbreDTO);
        
        arbre.setDatePlantation(arbreDTO.getDatePlantation());
        arbre.setChamp(nouveauChamp);
        
        Arbre updatedArbre = arbreRepository.save(arbre);
        log.info("Arbre modifié avec succès, ID: {}", updatedArbre.getId());
        
        return arbreMapper.toDTO(updatedArbre);
    }

    @Override
    public ArbreDTO getArbreById(Long id) {
        log.info("Récupération arbre ID: {}", id);
        return arbreMapper.toDTO(getArbre(id));
    }

    @Override
    public List<ArbreDTO> getAllArbres() {
        log.info("Récupération de tous les arbres");
        return arbreMapper.toDTOList(arbreRepository.findAll());
    }

    @Override
    public void supprimerArbre(Long id) {
        log.info("Suppression arbre ID: {}", id);
        if (!arbreRepository.existsById(id)) {
            throw new ArbreException.ArbreInexistantException();
        }
        arbreRepository.deleteById(id);
        log.info("Arbre supprimé avec succès, ID: {}", id);
    }

    @Override
    public List<ArbreDTO> getArbresByChamp(Long champId) {
        log.info("Récupération des arbres pour le champ ID: {}", champId);
        if (!champRepository.existsById(champId)) {
            throw new ArbreException.ChampInexistantException();
        }
        return arbreMapper.toDTOList(arbreRepository.findByChampId(champId));
    }

    private Champ getChamp(Long champId) {
        return champRepository.findById(champId)
            .orElseThrow(ArbreException.ChampInexistantException::new);
    }

    private Arbre getArbre(Long id) {
        return arbreRepository.findById(id)
            .orElseThrow(ArbreException.ArbreInexistantException::new);
    }

    private void validerPeriodePlantation(ArbreDTO arbreDTO) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(arbreDTO.getDatePlantation());
        int moisPlantation = cal.get(Calendar.MONTH) + 1;
        
        if (moisPlantation < 3 || moisPlantation > 5) {
            throw new ArbreException.PeriodePlantationInvalideException();
        }
    }

    private void validerDensiteArbres(Champ champ) {
        long nombreArbres = arbreRepository.countByChampId(champ.getId());
        double superficieHectares = champ.getSuperficie();
        int maxArbres = (int) (superficieHectares * 100);

        if (nombreArbres >= maxArbres) {
            throw new ArbreException.DensiteMaximaleException();
        }
    }
}

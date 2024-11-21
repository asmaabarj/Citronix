package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.ChampDTO;
import com.projet.citronix.exceptions.ChampException;
import com.projet.citronix.mapper.ChampMapper;
import com.projet.citronix.models.entities.Champ;
import com.projet.citronix.models.entities.Ferme;
import com.projet.citronix.repositories.ChampRepository;
import com.projet.citronix.repositories.FermeRepository;
import com.projet.citronix.services.interfaces.ChampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChampServiceImpl implements ChampService {
    private final ChampRepository champRepository;
    private final FermeRepository fermeRepository;
    private final ChampMapper champMapper;

    @Override
    public ChampDTO creer(ChampDTO champDTO) {
        log.info("Création d'un nouveau champ");
        Ferme ferme = getFerme(champDTO.getFermeId());
        validerContraintes(champDTO, ferme);
        
        Champ champ = champMapper.toEntity(champDTO);
        champ.setFerme(ferme);
        
        Champ savedChamp = champRepository.save(champ);
        log.info("Champ créé avec succès - ID: {}", savedChamp.getId());
        return champMapper.toDto(savedChamp);
    }

    @Override
    public ChampDTO modifier(Long id, ChampDTO champDTO) {
        log.info("Modification du champ - ID: {}", id);
        Champ champ = getChamp(id);
        Ferme ferme = getFerme(champDTO.getFermeId());
        
        Double ancienneSuperficie = champ.getSuperficie();
        Double superficieTotale = getSuperficieTotale(ferme.getId()) - ancienneSuperficie;
        
        if (superficieTotale + champDTO.getSuperficie() >= ferme.getSuperficie()) {
            log.error("La superficie totale dépasserait la superficie de la ferme");
            throw new ChampException.SuperficieTotaleException();
        }
        
        validerSuperficie(champDTO.getSuperficie(), ferme.getSuperficie());
        
        champ.setNom(champDTO.getNom());
        champ.setSuperficie(champDTO.getSuperficie());
        champ.setFerme(ferme);
        
        Champ updatedChamp = champRepository.save(champ);
        log.info("Champ modifié avec succès - ID: {}", id);
        return champMapper.toDto(updatedChamp);
    }

    @Override
    public ChampDTO recupererParId(Long id) {
        log.info("Récupération du champ - ID: {}", id);
        return champMapper.toDto(getChamp(id));
    }

    @Override
    public void supprimer(Long id) {
        log.info("Suppression du champ - ID: {}", id);
        if (!champRepository.existsById(id)) {
            throw new EntityNotFoundException("Champ non trouvé");
        }
        champRepository.deleteById(id);
        log.info("Champ supprimé avec succès - ID: {}", id);
    }

    @Override
    public List<ChampDTO> recupererParFermeId(Long fermeId) {
        log.info("Récupération des champs de la ferme - ID: {}", fermeId);
        getFerme(fermeId); // Vérifie si la ferme existe
        return champMapper.toDtoList(champRepository.findByFermeId(fermeId));
    }

    @Override
    public Page<ChampDTO> recupererTout(Pageable pageable) {
        log.info("Récupération de tous les champs");
        return champRepository.findAll(pageable).map(champMapper::toDto);
    }

    private Ferme getFerme(Long fermeId) {
        return fermeRepository.findById(fermeId)
                .orElseThrow(() -> new EntityNotFoundException("Ferme non trouvée"));
    }

    private Champ getChamp(Long champId) {
        return champRepository.findById(champId)
                .orElseThrow(() -> new EntityNotFoundException("Champ non trouvé"));
    }

    private void validerContraintes(ChampDTO champDTO, Ferme ferme) {
        validerNombreMaxChamps(ferme.getId());
        validerSuperficie(champDTO.getSuperficie(), ferme.getSuperficie());
        validerSuperficieTotale(champDTO.getSuperficie(), ferme);
    }

    private void validerNombreMaxChamps(Long fermeId) {
        if (champRepository.countByFermeId(fermeId) >= 10) {
            log.error("Nombre maximal de champs atteint");
            throw new ChampException.NombreMaximalChampsException();
        }
    }

    private void validerSuperficie(Double superficie, Double superficieFerme) {
        if (superficie < 0.1) {
            throw new ChampException.SuperficieMinimaleException();
        }
        if (superficie > superficieFerme * 0.5) {
            throw new ChampException.SuperficieMaximaleException();
        }
    }

    private void validerSuperficieTotale(Double nouvelleSuperficie, Ferme ferme) {
        Double superficieTotale = getSuperficieTotale(ferme.getId());
        if (superficieTotale + nouvelleSuperficie >= ferme.getSuperficie()) {
            throw new ChampException.SuperficieTotaleException();
        }
    }

    private Double getSuperficieTotale(Long fermeId) {
        Double superficieTotale = champRepository.sumSuperficieByFermeId(fermeId);
        return superficieTotale == null ? 0.0 : superficieTotale;
    }
}

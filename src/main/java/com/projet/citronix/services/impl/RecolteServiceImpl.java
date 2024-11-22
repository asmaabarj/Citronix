package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.RecolteDTO;
import com.projet.citronix.exceptions.RecolteException;
import com.projet.citronix.mapper.RecolteMapper;
import com.projet.citronix.models.entities.DetailRecolte;
import com.projet.citronix.models.entities.Recolte;
import com.projet.citronix.models.enums.Saison;
import com.projet.citronix.repositories.DetailRecolteRepository;
import com.projet.citronix.repositories.RecolteRepository;
import com.projet.citronix.services.interfaces.RecolteService;
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
public class RecolteServiceImpl implements RecolteService {
    private final RecolteRepository recolteRepository;
    private final DetailRecolteRepository detailRecolteRepository;
    private final RecolteMapper recolteMapper;

    @Override
    public RecolteDTO creer(RecolteDTO recolteDTO) {
        log.info("Création d'une nouvelle récolte pour la saison: {}", recolteDTO.getSaison());
        
        if (recolteRepository.existsBySaison(recolteDTO.getSaison())) {
            throw new RecolteException.RecolteExistantePourSaisonException();
        }
        
        Recolte recolte = recolteMapper.toEntity(recolteDTO);
        recolte.setQuantiteTotale(0.0);
        Recolte savedRecolte = recolteRepository.save(recolte);
        
        log.info("Récolte créée avec succès, ID: {}", savedRecolte.getId());
        return recolteMapper.toDto(savedRecolte);
    }

    @Override
    public RecolteDTO modifier(Long id, RecolteDTO recolteDTO) {
        log.info("Modification de la récolte ID: {}", id);
        
        Recolte recolte = getRecolte(id);
        
        if (!recolte.getSaison().equals(recolteDTO.getSaison()) && 
            recolteRepository.existsBySaison(recolteDTO.getSaison())) {
            throw new RecolteException.RecolteExistantePourSaisonException();
        }
        
        recolte.setDateRecolte(recolteDTO.getDateRecolte());
        recolte.setSaison(recolteDTO.getSaison());
        
        Recolte updatedRecolte = recolteRepository.save(recolte);
        log.info("Récolte modifiée avec succès, ID: {}", updatedRecolte.getId());
        
        return recolteMapper.toDto(updatedRecolte);
    }

    @Override
    public RecolteDTO recupererParId(Long id) {
        log.info("Récupération de la récolte ID: {}", id);
        return recolteMapper.toDto(getRecolte(id));
    }

    @Override
    public void supprimer(Long id) {
        log.info("Suppression de la récolte ID: {}", id);
        if (!recolteRepository.existsById(id)) {
            throw new RecolteException.RecolteInexistanteException();
        }
        recolteRepository.deleteById(id);
        log.info("Récolte supprimée avec succès, ID: {}", id);
    }

    @Override
    public List<RecolteDTO> recupererTout() {
        log.info("Récupération de toutes les récoltes");
        return recolteMapper.toDtoList(recolteRepository.findAll());
    }

    @Override
    public Page<RecolteDTO> rechercher(Pageable pageable) {
        log.info("Recherche des récoltes avec pagination");
        return recolteRepository.findAll(pageable).map(recolteMapper::toDto);
    }

    @Override
    public List<RecolteDTO> recupererParSaison(Saison saison) {
        log.info("Récupération des récoltes pour la saison: {}", saison);
        return recolteMapper.toDtoList(recolteRepository.findBySaison(saison));
    }

    @Override
    public void mettreAJourQuantiteTotale(Long recolteId) {
        log.info("Mise à jour de la quantité totale pour la récolte ID: {}", recolteId);
        Recolte recolte = getRecolte(recolteId);
        
        Double quantiteTotale = recolte.getDetails().stream()
            .mapToDouble(DetailRecolte::getQuantiteParArbre)
            .sum();
            
        recolte.setQuantiteTotale(quantiteTotale);
        recolteRepository.save(recolte);
        
        log.info("Quantité totale mise à jour: {} kg", quantiteTotale);
    }

    private Recolte getRecolte(Long id) {
        return recolteRepository.findById(id)
            .orElseThrow(RecolteException.RecolteInexistanteException::new);
    }
}

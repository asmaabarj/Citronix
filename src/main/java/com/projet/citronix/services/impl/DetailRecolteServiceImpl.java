package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.DetailRecolteDTO;
import com.projet.citronix.exceptions.DetailRecolteException;
import com.projet.citronix.mapper.DetailRecolteMapper;
import com.projet.citronix.models.entities.Arbre;
import com.projet.citronix.models.entities.DetailRecolte;
import com.projet.citronix.models.entities.Recolte;
import com.projet.citronix.repositories.ArbreRepository;
import com.projet.citronix.repositories.DetailRecolteRepository;
import com.projet.citronix.repositories.RecolteRepository;
import com.projet.citronix.services.interfaces.DetailRecolteService;
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
public class DetailRecolteServiceImpl implements DetailRecolteService {
    private final DetailRecolteRepository detailRecolteRepository;
    private final RecolteRepository recolteRepository;
    private final ArbreRepository arbreRepository;
    private final DetailRecolteMapper detailRecolteMapper;
    private final RecolteService recolteService;

    @Override
    public DetailRecolteDTO creer(DetailRecolteDTO detailRecolteDTO) {
        log.info("Création d'un nouveau détail de récolte");
        
        Recolte recolte = getRecolte(detailRecolteDTO.getRecolteId());
        Arbre arbre = getArbre(detailRecolteDTO.getArbreId());
        
        validerDetailRecolte(arbre, recolte);
        
        DetailRecolte detailRecolte = detailRecolteMapper.toEntity(detailRecolteDTO);
        detailRecolte.setArbre(arbre);
        detailRecolte.setRecolte(recolte);
        
        DetailRecolte savedDetail = detailRecolteRepository.save(detailRecolte);
        recolteService.mettreAJourQuantiteTotale(recolte.getId());
        
        log.info("Détail de récolte créé avec succès, ID: {}", savedDetail.getId());
        return detailRecolteMapper.toDto(savedDetail);
    }

    @Override
    public DetailRecolteDTO modifier(Long id, DetailRecolteDTO detailRecolteDTO) {
        log.info("Modification du détail de récolte ID: {}", id);
        
        DetailRecolte detailRecolte = getDetailRecolte(id);
        Recolte recolte = getRecolte(detailRecolteDTO.getRecolteId());
        Arbre arbre = getArbre(detailRecolteDTO.getArbreId());
        
        if (!detailRecolte.getRecolte().getId().equals(detailRecolteDTO.getRecolteId()) ||
            !detailRecolte.getArbre().getId().equals(detailRecolteDTO.getArbreId())) {
            validerDetailRecolte(arbre, recolte);
        }
        
        detailRecolte.setArbre(arbre);
        detailRecolte.setRecolte(recolte);
        
        DetailRecolte updatedDetail = detailRecolteRepository.save(detailRecolte);
        recolteService.mettreAJourQuantiteTotale(recolte.getId());
        
        log.info("Détail de récolte modifié avec succès, ID: {}", updatedDetail.getId());
        return detailRecolteMapper.toDto(updatedDetail);
    }

    @Override
    public DetailRecolteDTO recupererParId(Long id) {
        log.info("Récupération du détail de récolte ID: {}", id);
        return detailRecolteMapper.toDto(getDetailRecolte(id));
    }

    @Override
    public void supprimer(Long id) {
        log.info("Suppression du détail de récolte ID: {}", id);
        DetailRecolte detailRecolte = getDetailRecolte(id);
        detailRecolteRepository.deleteById(id);
        recolteService.mettreAJourQuantiteTotale(detailRecolte.getRecolte().getId());
        log.info("Détail de récolte supprimé avec succès, ID: {}", id);
    }

    @Override
    public List<DetailRecolteDTO> recupererTout() {
        log.info("Récupération de tous les détails de récolte");
        return detailRecolteMapper.toDtoList(detailRecolteRepository.findAll());
    }

    @Override
    public Page<DetailRecolteDTO> rechercher(Pageable pageable) {
        log.info("Recherche des détails de récolte avec pagination");
        return detailRecolteRepository.findAll(pageable).map(detailRecolteMapper::toDto);
    }

    @Override
    public List<DetailRecolteDTO> recupererParRecolte(Long recolteId) {
        log.info("Récupération des détails pour la récolte ID: {}", recolteId);
        return detailRecolteMapper.toDtoList(detailRecolteRepository.findByRecolteId(recolteId));
    }

    @Override
    public List<DetailRecolteDTO> recupererParArbre(Long arbreId) {
        log.info("Récupération des détails pour l'arbre ID: {}", arbreId);
        return detailRecolteMapper.toDtoList(detailRecolteRepository.findByArbreId(arbreId));
    }

    private DetailRecolte getDetailRecolte(Long id) {
        return detailRecolteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Détail de récolte non trouvé"));
    }

    private Recolte getRecolte(Long id) {
        return recolteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Récolte non trouvée"));
    }

    private Arbre getArbre(Long id) {
        return arbreRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Arbre non trouvé"));
    }

    private void validerDetailRecolte(Arbre arbre, Recolte recolte) {
        if (detailRecolteRepository.existsByArbreIdAndRecolteId(arbre.getId(), recolte.getId())) {
            throw new DetailRecolteException("Un détail de récolte existe déjà pour cet arbre dans cette récolte");
        }
    }
} 
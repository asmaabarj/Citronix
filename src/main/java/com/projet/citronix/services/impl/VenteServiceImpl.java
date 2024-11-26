package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.VenteDTO;
import com.projet.citronix.exceptions.VenteException;
import com.projet.citronix.mapper.VenteMapper;
import com.projet.citronix.models.entities.Recolte;
import com.projet.citronix.models.entities.Vente;
import com.projet.citronix.repositories.RecolteRepository;
import com.projet.citronix.repositories.VenteRepository;
import com.projet.citronix.services.interfaces.VenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VenteServiceImpl implements VenteService {
    private final VenteRepository venteRepository;
    private final RecolteRepository recolteRepository;
    private final VenteMapper venteMapper;

    @Override
    public VenteDTO creer(VenteDTO venteDTO) {
        log.info("Création d'une nouvelle vente");
        
        Recolte recolte = getRecolte(venteDTO.getRecolteId());
        validerVente(venteDTO, recolte);
        
        Vente vente = venteMapper.toEntity(venteDTO);
        vente.setRecolte(recolte);
        
        Vente savedVente = venteRepository.save(vente);
        log.info("Vente créée avec succès, ID: {}", savedVente.getId());
        
        return venteMapper.toDto(savedVente);
    }

    @Override
    public VenteDTO modifier(Long id, VenteDTO venteDTO) {
        log.info("Modification de la vente ID: {}", id);
        
        Vente vente = getVente(id);
        Recolte recolte = getRecolte(venteDTO.getRecolteId());
        
        validerVente(venteDTO, recolte);
        
        venteMapper.updateEntity(venteDTO, vente);
        vente.setRecolte(recolte);
        
        Vente updatedVente = venteRepository.save(vente);
        log.info("Vente modifiée avec succès, ID: {}", updatedVente.getId());
        
        return venteMapper.toDto(updatedVente);
    }

    @Override
    public VenteDTO recupererParId(Long id) {
        log.info("Récupération de la vente ID: {}", id);
        return venteMapper.toDto(getVente(id));
    }

    @Override
    public void supprimer(Long id) {
        log.info("Suppression de la vente ID: {}", id);
        if (!venteRepository.existsById(id)) {
            throw new VenteException.VenteInexistanteException();
        }
        venteRepository.deleteById(id);
        log.info("Vente supprimée avec succès, ID: {}", id);
    }

    @Override
    public List<VenteDTO> recupererTout() {
        log.info("Récupération de toutes les ventes");
        return venteMapper.toDtoList(venteRepository.findAll());
    }


    @Override
    public List<VenteDTO> recupererParRecolte(Long recolteId) {
        log.info("Récupération des ventes pour la récolte ID: {}", recolteId);
        return venteMapper.toDtoList(venteRepository.findByRecolteId(recolteId));
    }

    private Vente getVente(Long id) {
        return venteRepository.findById(id)
            .orElseThrow(VenteException.VenteInexistanteException::new);
    }

    private Recolte getRecolte(Long id) {
        return recolteRepository.findById(id)
                .orElseThrow(VenteException.VenteInexistanteException::new);
    }

    private void validerVente(VenteDTO venteDTO, Recolte recolte) {
        Date now = Calendar.getInstance().getTime();
        
        if (venteDTO.getDateVente().after(now)) {
            throw new VenteException.DateVenteFutureException();
        }
        
        if (venteDTO.getDateVente().before(recolte.getDateRecolte())) {
            throw new VenteException.DateVenteAvantRecolteException();
        }
    }
}

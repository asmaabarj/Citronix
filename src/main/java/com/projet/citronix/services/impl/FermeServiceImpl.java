package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.FermeDTO;
import com.projet.citronix.exceptions.FermeException;
import com.projet.citronix.mapper.FermeMapper;
import com.projet.citronix.models.entities.Champ;
import com.projet.citronix.models.entities.Ferme;
import com.projet.citronix.repositories.FermeRepository;
import com.projet.citronix.services.interfaces.FermeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FermeServiceImpl implements FermeService {
    private final FermeRepository fermeRepository;
    private final FermeMapper fermeMapper;

    @Override
    public FermeDTO creer(FermeDTO fermeDTO) {
        log.info("Début création ferme: {}", fermeDTO.getNom());
        
        if (fermeRepository.existsByNom(fermeDTO.getNom())) {
            log.error("Une ferme avec le nom {} existe déjà", fermeDTO.getNom());
            throw new FermeException.NomExistantException();
        }

        if (fermeDTO.getSuperficie() < 0.2) {
            log.error("Superficie invalide: {}", fermeDTO.getSuperficie());
            throw new FermeException.SuperficieInvalideException();
        }

        Ferme ferme = fermeMapper.toEntity(fermeDTO);
        Ferme savedFerme = fermeRepository.save(ferme);
        log.info("Ferme créée avec succès, ID: {}", savedFerme.getId());
        
        return fermeMapper.toDto(savedFerme);
    }

    @Override
    public FermeDTO modifier(Long id, FermeDTO fermeDTO) {
        log.info("Début modification ferme ID: {}", id);
        
        Ferme ferme = fermeRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Ferme non trouvée avec ID: {}", id);
                return new EntityNotFoundException("Ferme non trouvée");
            });

        if (!ferme.getNom().equals(fermeDTO.getNom()) && 
            fermeRepository.existsByNom(fermeDTO.getNom())) {
            log.error("Une ferme avec le nom {} existe déjà", fermeDTO.getNom());
            throw new FermeException.NomExistantException();
        }

        ferme.setNom(fermeDTO.getNom());
        ferme.setLocalisation(fermeDTO.getLocalisation());
        ferme.setSuperficie(fermeDTO.getSuperficie());
        ferme.setDateCreation(fermeDTO.getDateCreation());

        Ferme updatedFerme = fermeRepository.save(ferme);
        log.info("Ferme modifiée avec succès, ID: {}", updatedFerme.getId());
        
        return fermeMapper.toDto(updatedFerme);
    }

    @Override
    public FermeDTO recupererParId(Long id) {
        log.info("Récupération ferme ID: {}", id);
        
        Ferme ferme = fermeRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Ferme non trouvée avec ID: {}", id);
                return new EntityNotFoundException("Ferme non trouvée");
            });
            
        return fermeMapper.toDto(ferme);
    }

    @Override
    public void supprimer(Long id) {
        log.info("Suppression ferme ID: {}", id);
        
        if (!fermeRepository.existsById(id)) {
            log.error("Ferme non trouvée avec ID: {}", id);
            throw new EntityNotFoundException("Ferme non trouvée");
        }
        
        fermeRepository.deleteById(id);
        log.info("Ferme supprimée avec succès, ID: {}", id);
    }

    @Override
    public List<FermeDTO> recupererTout() {
        log.info("Récupération de toutes les fermes");
        return fermeMapper.toDtoList(fermeRepository.findAll());
    }

    @Override
    public Page<FermeDTO> rechercher(Map<String, String> criteres, Pageable pageable) {
        log.info("Recherche des fermes avec critères: {}", criteres);
        
        Specification<Ferme> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            criteres.forEach((key, value) -> {
                switch (key) {
                    case "nom":
                        predicates.add(cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                        break;
                    case "localisation":
                        predicates.add(cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                        break;
                    case "superficieMin":
                        predicates.add(cb.greaterThanOrEqualTo(root.get("superficie"), Double.valueOf(value)));
                        break;
                    case "superficieMax":
                        predicates.add(cb.lessThanOrEqualTo(root.get("superficie"), Double.valueOf(value)));
                        break;
                }
            });
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        Page<Ferme> fermePage = fermeRepository.findAll(spec, pageable);
        return fermePage.map(fermeMapper::toDto);
    }

    private void validerSuperficie(FermeDTO fermeDTO) {
        if (fermeDTO.getSuperficie() < 0.2) {
            log.error("Superficie invalide: {}", fermeDTO.getSuperficie());
            throw new FermeException.SuperficieInvalideException();
        }
    }

    private void validerModificationSuperficie(Ferme ferme, Double nouvelleSuperficie) {
        Double superficieTotaleChamps = ferme.getChamps().stream()
                .mapToDouble(Champ::getSuperficie)
                .sum();
        if (superficieTotaleChamps >= nouvelleSuperficie) {
            throw new FermeException.SuperficieInvalideException("La nouvelle superficie est inférieure à la superficie totale des champs");
        }
    }
}

package com.projet.citronix.controllers;

import com.projet.citronix.dtos.DetailRecolteDTO;
import com.projet.citronix.services.interfaces.DetailRecolteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/detail-recoltes")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DetailRecolteController {
    private final DetailRecolteService detailRecolteService;

    @PostMapping
    public ResponseEntity<DetailRecolteDTO> creer(@Valid @RequestBody DetailRecolteDTO detailRecolteDTO) {
        log.info("Création d'un nouveau détail de récolte");
        return new ResponseEntity<>(detailRecolteService.creer(detailRecolteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailRecolteDTO> modifier(@PathVariable Long id, 
                                                   @Valid @RequestBody DetailRecolteDTO detailRecolteDTO) {
        log.info("Modification du détail de récolte avec l'ID: {}", id);
        return ResponseEntity.ok(detailRecolteService.modifier(id, detailRecolteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailRecolteDTO> recupererParId(@PathVariable Long id) {
        log.info("Récupération du détail de récolte avec l'ID: {}", id);
        return ResponseEntity.ok(detailRecolteService.recupererParId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        log.info("Suppression du détail de récolte avec l'ID: {}", id);
        detailRecolteService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DetailRecolteDTO>> recupererTout() {
        log.info("Récupération de tous les détails de récolte");
        return ResponseEntity.ok(detailRecolteService.recupererTout());
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<DetailRecolteDTO>> rechercher(Pageable pageable) {
        log.info("Recherche des détails de récolte avec pagination");
        return ResponseEntity.ok(detailRecolteService.rechercher(pageable));
    }

    @GetMapping("/recolte/{recolteId}")
    public ResponseEntity<List<DetailRecolteDTO>> recupererParRecolte(@PathVariable Long recolteId) {
        log.info("Récupération des détails pour la récolte ID: {}", recolteId);
        return ResponseEntity.ok(detailRecolteService.recupererParRecolte(recolteId));
    }

    @GetMapping("/arbre/{arbreId}")
    public ResponseEntity<List<DetailRecolteDTO>> recupererParArbre(@PathVariable Long arbreId) {
        log.info("Récupération des détails pour l'arbre ID: {}", arbreId);
        return ResponseEntity.ok(detailRecolteService.recupererParArbre(arbreId));
    }
} 
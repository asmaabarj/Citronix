package com.projet.citronix.controllers;

import com.projet.citronix.dtos.VenteDTO;
import com.projet.citronix.services.interfaces.VenteService;
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
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
@Slf4j
@Validated
public class VenteController {
    private final VenteService venteService;

    @PostMapping
    public ResponseEntity<VenteDTO> creer(@Valid @RequestBody VenteDTO venteDTO) {
        log.info("Création d'une nouvelle vente");
        return new ResponseEntity<>(venteService.creer(venteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenteDTO> modifier(@PathVariable Long id, @Valid @RequestBody VenteDTO venteDTO) {
        log.info("Modification de la vente avec l'ID: {}", id);
        return ResponseEntity.ok(venteService.modifier(id, venteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenteDTO> recupererParId(@PathVariable Long id) {
        log.info("Récupération de la vente avec l'ID: {}", id);
        return ResponseEntity.ok(venteService.recupererParId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        log.info("Suppression de la vente avec l'ID: {}", id);
        venteService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VenteDTO>> recupererTout() {
        log.info("Récupération de toutes les ventes");
        return ResponseEntity.ok(venteService.recupererTout());
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<VenteDTO>> rechercher(Pageable pageable) {
        log.info("Recherche des ventes avec pagination");
        return ResponseEntity.ok(venteService.rechercher(pageable));
    }

    @GetMapping("/recolte/{recolteId}")
    public ResponseEntity<List<VenteDTO>> recupererParRecolte(@PathVariable Long recolteId) {
        log.info("Récupération des ventes pour la récolte ID: {}", recolteId);
        return ResponseEntity.ok(venteService.recupererParRecolte(recolteId));
    }
}

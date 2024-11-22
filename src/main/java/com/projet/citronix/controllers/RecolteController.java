package com.projet.citronix.controllers;

import com.projet.citronix.dtos.RecolteDTO;
import com.projet.citronix.models.enums.Saison;
import com.projet.citronix.services.interfaces.RecolteService;
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
@RequestMapping("/api/recoltes")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RecolteController {
    private final RecolteService recolteService;

    @PostMapping
    public ResponseEntity<RecolteDTO> creer(@Valid @RequestBody RecolteDTO recolteDTO) {
        log.info("Création d'une nouvelle récolte");
        return new ResponseEntity<>(recolteService.creer(recolteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecolteDTO> modifier(@PathVariable Long id, @Valid @RequestBody RecolteDTO recolteDTO) {
        log.info("Modification de la récolte avec l'ID: {}", id);
        return ResponseEntity.ok(recolteService.modifier(id, recolteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecolteDTO> recupererParId(@PathVariable Long id) {
        log.info("Récupération de la récolte avec l'ID: {}", id);
        return ResponseEntity.ok(recolteService.recupererParId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        log.info("Suppression de la récolte avec l'ID: {}", id);
        recolteService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RecolteDTO>> recupererTout() {
        log.info("Récupération de toutes les récoltes");
        return ResponseEntity.ok(recolteService.recupererTout());
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<RecolteDTO>> rechercher(Pageable pageable) {
        log.info("Recherche des récoltes avec pagination");
        return ResponseEntity.ok(recolteService.rechercher(pageable));
    }

    @GetMapping("/saison/{saison}")
    public ResponseEntity<List<RecolteDTO>> recupererParSaison(@PathVariable Saison saison) {
        log.info("Récupération des récoltes pour la saison: {}", saison);
        return ResponseEntity.ok(recolteService.recupererParSaison(saison));
    }
}

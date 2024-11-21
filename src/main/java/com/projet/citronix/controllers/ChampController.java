package com.projet.citronix.controllers;

import com.projet.citronix.dtos.ChampDTO;
import com.projet.citronix.services.interfaces.ChampService;
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
@RequestMapping("/api/champs")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChampController {
    private final ChampService champService;

    @PostMapping
    public ResponseEntity<ChampDTO> creer(@Valid @RequestBody ChampDTO champDTO) {
        log.info("Création d'un nouveau champ");
        return new ResponseEntity<>(champService.creer(champDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChampDTO> modifier(@PathVariable Long id, @Valid @RequestBody ChampDTO champDTO) {
        log.info("Modification du champ avec l'ID: {}", id);
        return ResponseEntity.ok(champService.modifier(id, champDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChampDTO> recupererParId(@PathVariable Long id) {
        log.info("Récupération du champ avec l'ID: {}", id);
        return ResponseEntity.ok(champService.recupererParId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        log.info("Suppression du champ avec l'ID: {}", id);
        champService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ferme/{fermeId}")
    public ResponseEntity<List<ChampDTO>> recupererParFermeId(@PathVariable Long fermeId) {
        log.info("Récupération des champs pour la ferme avec l'ID: {}", fermeId);
        return ResponseEntity.ok(champService.recupererParFermeId(fermeId));
    }

    @GetMapping
    public ResponseEntity<Page<ChampDTO>> recupererTout(Pageable pageable) {
        log.info("Récupération de tous les champs");
        return ResponseEntity.ok(champService.recupererTout(pageable));
    }
}

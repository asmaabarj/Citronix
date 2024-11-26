package com.projet.citronix.controllers;

import com.projet.citronix.dtos.FermeDTO;
import com.projet.citronix.services.interfaces.FermeService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/fermes")
@RequiredArgsConstructor
@Slf4j
@Validated
public class FermeController {
    private final FermeService fermeService;

    @PostMapping
    public ResponseEntity<FermeDTO> creer(@Valid @RequestBody FermeDTO fermeDTO) {
        log.info("Création d'une nouvelle ferme");
        return new ResponseEntity<>(fermeService.creer(fermeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FermeDTO> modifier(@PathVariable Long id, @Valid @RequestBody FermeDTO fermeDTO) {
        log.info("Modification de la ferme avec l'ID: {}", id);
        return ResponseEntity.ok(fermeService.modifier(id, fermeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FermeDTO> recupererParId(@PathVariable Long id) {
        log.info("Récupération de la ferme avec l'ID: {}", id);
        return ResponseEntity.ok(fermeService.recupererParId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        log.info("Suppression de la ferme avec l'ID: {}", id);
        fermeService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FermeDTO>> recupererTout() {
        log.info("Récupération de toutes les fermes");
        return ResponseEntity.ok(fermeService.recupererTout());
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<FermeDTO>> rechercher(@RequestParam Map<String, String> criteres) {
        log.info("Recherche des fermes avec critères: {}", criteres);
        return ResponseEntity.ok(fermeService.rechercher(criteres));
    }

    @GetMapping("/page")
    public ResponseEntity<List<FermeDTO>> recupererToutAvecPagination(Pageable pageable) {
        log.info("Récupération de toutes les fermes avec pagination");
        return ResponseEntity.ok(fermeService.recupererTout(pageable));
    }
}

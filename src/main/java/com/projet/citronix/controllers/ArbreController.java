package com.projet.citronix.controllers;

import com.projet.citronix.dtos.ArbreDTO;
import com.projet.citronix.services.interfaces.ArbreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/arbres")
@RequiredArgsConstructor
public class ArbreController {
    private final ArbreService arbreService;

    @PostMapping
    public ResponseEntity<ArbreDTO> creerArbre(@Valid @RequestBody ArbreDTO arbreDTO) {
        return new ResponseEntity<>(arbreService.creerArbre(arbreDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArbreDTO> modifierArbre(@PathVariable Long id, 
                                                @Valid @RequestBody ArbreDTO arbreDTO) {
        return ResponseEntity.ok(arbreService.modifierArbre(id, arbreDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbreDTO> getArbre(@PathVariable Long id) {
        return ResponseEntity.ok(arbreService.getArbreById(id));
    }

    @GetMapping
    public ResponseEntity<List<ArbreDTO>> getAllArbres() {
        return ResponseEntity.ok(arbreService.getAllArbres());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerArbre(@PathVariable Long id) {
        arbreService.supprimerArbre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/champ/{champId}")
    public ResponseEntity<List<ArbreDTO>> getArbresByChamp(@PathVariable Long champId) {
        return ResponseEntity.ok(arbreService.getArbresByChamp(champId));
    }
}

package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.FermeDTO;
import com.projet.citronix.exceptions.FermeException;
import com.projet.citronix.mapper.FermeMapper;
import com.projet.citronix.models.entities.Ferme;
import com.projet.citronix.repositories.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FermeServiceImplTest {

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private FermeMapper fermeMapper;

    @InjectMocks
    private FermeServiceImpl fermeService;

    private FermeDTO fermeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fermeDTO = new FermeDTO(1L, "Ferme Test", "Localisation Test", 10.0, new Date());
    }

    @Test
    void creer_ShouldCreateFerme_WhenValid() {
        when(fermeRepository.existsByNom(fermeDTO.getNom())).thenReturn(false);
        when(fermeMapper.toEntity(fermeDTO)).thenReturn(new Ferme());
        when(fermeRepository.save(any())).thenReturn(new Ferme());
        when(fermeMapper.toDto(any())).thenReturn(fermeDTO);

        FermeDTO createdFerme = fermeService.creer(fermeDTO);

        assertNotNull(createdFerme);
        assertEquals(fermeDTO.getNom(), createdFerme.getNom());
        verify(fermeRepository).save(any());
    }

    @Test
    void creer_ShouldThrowException_WhenFermeExists() {
        when(fermeRepository.existsByNom(fermeDTO.getNom())).thenReturn(true);

        FermeException.NomExistantException exception = assertThrows(FermeException.NomExistantException.class, () -> {
            fermeService.creer(fermeDTO);
        });

        assertEquals("Une ferme avec ce nom existe déjà", exception.getMessage());
    }

    @Test
    void modifier_ShouldUpdateFerme_WhenValid() {
        // Créez une instance de Ferme avec un nom valide
        Ferme existingFerme = new Ferme();
        existingFerme.setNom("Ferme Test");
        existingFerme.setLocalisation("Localisation Test");
        existingFerme.setSuperficie(10.0);
        existingFerme.setDateCreation(new Date());

        when(fermeRepository.findById(1L)).thenReturn(Optional.of(existingFerme));
        when(fermeMapper.toEntity(fermeDTO)).thenReturn(existingFerme);
        when(fermeRepository.save(any())).thenReturn(existingFerme);
        when(fermeMapper.toDto(any())).thenReturn(fermeDTO);

        FermeDTO updatedFerme = fermeService.modifier(1L, fermeDTO);

        assertNotNull(updatedFerme);
        assertEquals(fermeDTO.getNom(), updatedFerme.getNom());
        verify(fermeRepository).save(any());
    }

    @Test
    void modifier_ShouldThrowException_WhenFermeNotFound() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            fermeService.modifier(1L, fermeDTO);
        });

        assertEquals("Ferme non trouvée", exception.getMessage());
    }

    @Test
    void recupererParId_ShouldReturnFerme_WhenExists() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.of(new Ferme()));
        when(fermeMapper.toDto(any())).thenReturn(fermeDTO);

        FermeDTO foundFerme = fermeService.recupererParId(1L);

        assertNotNull(foundFerme);
        assertEquals(fermeDTO.getNom(), foundFerme.getNom());
    }

    @Test
    void recupererParId_ShouldThrowException_WhenNotFound() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            fermeService.recupererParId(1L);
        });

        assertEquals("Ferme non trouvée", exception.getMessage());
    }

    @Test
    void supprimer_ShouldDeleteFerme_WhenExists() {
        when(fermeRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> fermeService.supprimer(1L));
        verify(fermeRepository).deleteById(1L);
    }

    @Test
    void supprimer_ShouldThrowException_WhenNotFound() {
        when(fermeRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            fermeService.supprimer(1L);
        });

        assertEquals("Ferme non trouvée", exception.getMessage());
    }

    @Test
    void recupererTout_ShouldReturnListOfFermes() {
        when(fermeRepository.findAll()).thenReturn(Arrays.asList(new Ferme()));
        when(fermeMapper.toDtoList(anyList())).thenReturn(Arrays.asList(fermeDTO));

        List<FermeDTO> fermes = fermeService.recupererTout();

        assertNotNull(fermes);
        assertEquals(1, fermes.size());
        assertEquals(fermeDTO.getNom(), fermes.get(0).getNom());
    }
} 
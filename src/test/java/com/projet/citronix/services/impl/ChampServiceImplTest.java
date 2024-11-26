package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.ChampDTO;
import com.projet.citronix.exceptions.ChampException;
import com.projet.citronix.mapper.ChampMapper;
import com.projet.citronix.models.entities.Champ;
import com.projet.citronix.models.entities.Ferme;
import com.projet.citronix.repositories.ChampRepository;
import com.projet.citronix.repositories.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChampServiceImplTest {

    @Mock
    private ChampRepository champRepository;

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private ChampMapper champMapper;

    @InjectMocks
    private ChampServiceImpl champService;

    private ChampDTO champDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        champDTO = new ChampDTO(1L, "Champ Test", 1.0, 1L);
    }

    @Test
    void creer_ShouldCreateChamp_WhenValid() {
        Ferme ferme = new Ferme();
        ferme.setSuperficie(10.0);
        when(fermeRepository.findById(1L)).thenReturn(Optional.of(ferme));
        when(champMapper.toEntity(champDTO)).thenReturn(new Champ());
        when(champRepository.save(any())).thenReturn(new Champ());
        when(champMapper.toDto(any())).thenReturn(champDTO);

        ChampDTO createdChamp = champService.creer(champDTO);

        assertNotNull(createdChamp);
        assertEquals(champDTO.getNom(), createdChamp.getNom());
        verify(champRepository).save(any());
    }

    @Test
    void creer_ShouldThrowException_WhenFermeNotFound() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            champService.creer(champDTO);
        });

        assertEquals("Ferme non trouvée", exception.getMessage());
    }

    @Test
    void modifier_ShouldUpdateChamp_WhenValid() {
        Ferme ferme = new Ferme();
        ferme.setSuperficie(10.0); // Assurez-vous que la superficie est définie
        when(fermeRepository.findById(1L)).thenReturn(Optional.of(ferme));

        Champ existingChamp = new Champ();
        existingChamp.setId(1L);
        existingChamp.setNom("Champ Ancien");
        existingChamp.setSuperficie(5.0);
        when(champRepository.findById(1L)).thenReturn(Optional.of(existingChamp));

        when(champMapper.toEntity(champDTO)).thenReturn(existingChamp);
        when(champRepository.save(any())).thenReturn(existingChamp);
        when(champMapper.toDto(any())).thenReturn(champDTO);

        ChampDTO updatedChamp = champService.modifier(1L, champDTO);

        assertNotNull(updatedChamp);
        assertEquals(champDTO.getNom(), updatedChamp.getNom());
        verify(champRepository).save(any());
    }

    @Test
    void modifier_ShouldThrowException_WhenChampNotFound() {
        when(champRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            champService.modifier(1L, champDTO);
        });

        assertEquals("Champ non trouvé", exception.getMessage());
    }

    @Test
    void recupererParId_ShouldReturnChamp_WhenExists() {
        when(champRepository.findById(1L)).thenReturn(Optional.of(new Champ()));
        when(champMapper.toDto(any())).thenReturn(champDTO);

        ChampDTO foundChamp = champService.recupererParId(1L);

        assertNotNull(foundChamp);
        assertEquals(champDTO.getNom(), foundChamp.getNom());
    }

    @Test
    void recupererParId_ShouldThrowException_WhenNotFound() {
        when(champRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            champService.recupererParId(1L);
        });

        assertEquals("Champ non trouvé", exception.getMessage());
    }

    @Test
    void supprimer_ShouldDeleteChamp_WhenExists() {
        when(champRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> champService.supprimer(1L));
        verify(champRepository).deleteById(1L);
    }

    @Test
    void supprimer_ShouldThrowException_WhenNotFound() {
        when(champRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            champService.supprimer(1L);
        });

        assertEquals("Champ non trouvé", exception.getMessage());
    }

    @Test
    void recupererParFermeId_ShouldReturnListOfChamps_WhenExists() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.of(new Ferme()));
        when(champRepository.findByFermeId(1L)).thenReturn(Arrays.asList(new Champ()));
        when(champMapper.toDtoList(anyList())).thenReturn(Arrays.asList(champDTO));

        List<ChampDTO> champs = champService.recupererParFermeId(1L);

        assertNotNull(champs);
        assertEquals(1, champs.size());
        assertEquals(champDTO.getNom(), champs.get(0).getNom());
    }

    @Test
    void recupererParFermeId_ShouldThrowException_WhenFermeNotFound() {
        when(fermeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            champService.recupererParFermeId(1L);
        });

        assertEquals("Ferme non trouvée", exception.getMessage());
    }



    @Test
    void recupererTout_ShouldReturnPageOfChamps() {
        Champ champ = new Champ();
        champ.setNom("Champ Test");
        champ.setSuperficie(1.0);

        List<Champ> champsList = Arrays.asList(champ);
        Page<Champ> champsPage = new PageImpl<>(champsList);

        when(champRepository.findAll(any(Pageable.class))).thenReturn(champsPage);
        when(champMapper.toDto(any())).thenReturn(new ChampDTO(1L, "Champ Test", 1.0, 1L));

        Page<ChampDTO> result = champService.recupererTout(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Champ Test", result.getContent().get(0).getNom());
    }
} 
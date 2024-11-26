package com.projet.citronix.services.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.projet.citronix.dtos.ArbreDTO;
import com.projet.citronix.exceptions.ArbreException;
import com.projet.citronix.mapper.ArbreMapper;
import com.projet.citronix.models.entities.Arbre;
import com.projet.citronix.models.entities.Champ;
import com.projet.citronix.repositories.ArbreRepository;
import com.projet.citronix.repositories.ChampRepository;

class ArbreServiceImplTest {

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampRepository champRepository;

    @Mock
    private ArbreMapper arbreMapper;

    @InjectMocks
    private ArbreServiceImpl arbreService;

    private ArbreDTO arbreDTO;
    private Arbre arbre;
    private Champ champ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        champ = new Champ();
        champ.setId(1L);
        arbreDTO = new ArbreDTO(1L, new Date(), 1L, null, null, null);
        arbre = new Arbre(1L, new Date(), champ, null, null, null, null);
    }

    @Test
    void creerArbre_ShouldCreateArbre_WhenValid() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 15);
        Date datePlantationValide = calendar.getTime();
        arbreDTO.setDatePlantation(datePlantationValide);
        champ.setSuperficie(2.0);
        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        when(arbreMapper.toEntity(arbreDTO)).thenReturn(arbre);
        when(arbreRepository.save(any())).thenReturn(arbre);
        when(arbreMapper.toDTO(any())).thenReturn(arbreDTO);

        ArbreDTO createdArbre = arbreService.creerArbre(arbreDTO);

        assertNotNull(createdArbre);
        assertEquals(arbreDTO.getChampId(), createdArbre.getChampId());
        verify(arbreRepository).save(any());
    }

    @Test
    void creerArbre_ShouldThrowException_WhenChampNotFound() {
        when(champRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ArbreException.ChampInexistantException.class, () -> {
            arbreService.creerArbre(arbreDTO);
        });
    }

    @Test
    void modifierArbre_ShouldUpdateArbre_WhenValid() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 15);
        Date datePlantationValide = calendar.getTime();

        arbreDTO.setDatePlantation(datePlantationValide);

        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        when(arbreMapper.toEntity(arbreDTO)).thenReturn(arbre);
        when(arbreRepository.save(any())).thenReturn(arbre);
        when(arbreMapper.toDTO(any())).thenReturn(arbreDTO);

        ArbreDTO updatedArbre = arbreService.modifierArbre(1L, arbreDTO);

        assertNotNull(updatedArbre);
        assertEquals(arbreDTO.getChampId(), updatedArbre.getChampId());
        verify(arbreRepository).save(any());
    }

    @Test
    void modifierArbre_ShouldThrowException_WhenArbreNotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ArbreException.ArbreInexistantException.class, () -> {
            arbreService.modifierArbre(1L, arbreDTO);
        });
    }

    @Test
    void getArbreById_ShouldReturnArbre_WhenExists() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.of(arbre));
        when(arbreMapper.toDTO(any())).thenReturn(arbreDTO);

        ArbreDTO foundArbre = arbreService.getArbreById(1L);

        assertNotNull(foundArbre);
        assertEquals(arbreDTO.getId(), foundArbre.getId());
    }

    @Test
    void getArbreById_ShouldThrowException_WhenNotFound() {
        when(arbreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ArbreException.ArbreInexistantException.class, () -> {
            arbreService.getArbreById(1L);
        });
    }

    @Test
    void getAllArbres_ShouldReturnListOfArbres() {
        when(arbreRepository.findAll()).thenReturn(Arrays.asList(arbre));
        when(arbreMapper.toDTOList(any())).thenReturn(Arrays.asList(arbreDTO));

        List<ArbreDTO> arbres = arbreService.getAllArbres();

        assertNotNull(arbres);
        assertEquals(1, arbres.size());
    }

    @Test
    void supprimerArbre_ShouldDeleteArbre_WhenExists() {
        when(arbreRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> arbreService.supprimerArbre(1L));
        verify(arbreRepository).deleteById(1L);
    }

    @Test
    void supprimerArbre_ShouldThrowException_WhenNotFound() {
        when(arbreRepository.existsById(1L)).thenReturn(false);

        assertThrows(ArbreException.ArbreInexistantException.class, () -> {
            arbreService.supprimerArbre(1L);
        });
    }

    @Test
    void getArbresByChamp_ShouldReturnListOfArbres_WhenChampExists() {
        when(champRepository.existsById(1L)).thenReturn(true);
        when(arbreRepository.findByChampId(1L)).thenReturn(Arrays.asList(arbre));
        when(arbreMapper.toDTOList(any())).thenReturn(Arrays.asList(arbreDTO));

        List<ArbreDTO> arbres = arbreService.getArbresByChamp(1L);

        assertNotNull(arbres);
        assertEquals(1, arbres.size());
    }

    @Test
    void getArbresByChamp_ShouldThrowException_WhenChampNotFound() {
        when(champRepository.existsById(1L)).thenReturn(false);

        assertThrows(ArbreException.ChampInexistantException.class, () -> {
            arbreService.getArbresByChamp(1L);
        });
    }
}

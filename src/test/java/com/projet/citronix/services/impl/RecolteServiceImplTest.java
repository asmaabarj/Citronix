package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.RecolteDTO;
import com.projet.citronix.exceptions.RecolteException;
import com.projet.citronix.mapper.RecolteMapper;
import com.projet.citronix.models.entities.Recolte;
import com.projet.citronix.models.enums.Saison;
import com.projet.citronix.repositories.RecolteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecolteServiceImplTest {

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private RecolteMapper recolteMapper;

    @InjectMocks
    private RecolteServiceImpl recolteService;

    private RecolteDTO recolteDTO;
    private Recolte recolte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recolteDTO = new RecolteDTO(1L, new Date(), 0.0, Saison.PRINTEMPS, new ArrayList<>());
        recolte = new Recolte(1L, new Date(), 0.0, Saison.PRINTEMPS, null, null);

    }

    @Test
    void creer_ShouldCreateRecolte_WhenValid() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 15);
        Date validDate = calendar.getTime();

        recolteDTO.setDateRecolte(validDate);

        when(recolteRepository.existsBySaisonAndAnnee(any(), anyInt())).thenReturn(false);
        when(recolteMapper.toEntity(recolteDTO)).thenReturn(recolte);
        when(recolteRepository.save(any())).thenReturn(recolte);
        when(recolteMapper.toDto(any())).thenReturn(recolteDTO);

        RecolteDTO createdRecolte = recolteService.creer(recolteDTO);

        assertNotNull(createdRecolte);
        assertEquals(recolteDTO.getSaison(), createdRecolte.getSaison());
        verify(recolteRepository).save(any());
    }

    @Test
    void creer_ShouldThrowException_WhenRecolteExistsForSeason() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 15);
        Date validDate = calendar.getTime();

        recolteDTO.setDateRecolte(validDate);

        when(recolteRepository.existsBySaisonAndAnnee(any(), anyInt())).thenReturn(true);

        assertThrows(RecolteException.RecolteExistantePourSaisonException.class, () -> {
            recolteService.creer(recolteDTO);
        });
    }

    @Test
    void modifier_ShouldUpdateRecolte_WhenValid() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
        when(recolteMapper.toEntity(recolteDTO)).thenReturn(recolte);
        when(recolteRepository.save(any())).thenReturn(recolte);
        when(recolteMapper.toDto(any())).thenReturn(recolteDTO);

        RecolteDTO updatedRecolte = recolteService.modifier(1L, recolteDTO);

        assertNotNull(updatedRecolte);
        assertEquals(recolteDTO.getSaison(), updatedRecolte.getSaison());
        verify(recolteRepository).save(any());
    }

    @Test
    void modifier_ShouldThrowException_WhenRecolteNotFound() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecolteException.RecolteInexistanteException.class, () -> {
            recolteService.modifier(1L, recolteDTO);
        });
    }

    @Test
    void recupererParId_ShouldReturnRecolte_WhenExists() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
        when(recolteMapper.toDto(any())).thenReturn(recolteDTO);

        RecolteDTO foundRecolte = recolteService.recupererParId(1L);

        assertNotNull(foundRecolte);
        assertEquals(recolteDTO.getId(), foundRecolte.getId());
    }

    @Test
    void recupererParId_ShouldThrowException_WhenNotFound() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecolteException.RecolteInexistanteException.class, () -> {
            recolteService.recupererParId(1L);
        });
    }

    @Test
    void supprimer_ShouldDeleteRecolte_WhenExists() {
        when(recolteRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> recolteService.supprimer(1L));
        verify(recolteRepository).deleteById(1L);
    }

    @Test
    void supprimer_ShouldThrowException_WhenNotFound() {
        when(recolteRepository.existsById(1L)).thenReturn(false);

        assertThrows(RecolteException.RecolteInexistanteException.class, () -> {
            recolteService.supprimer(1L);
        });
    }

    @Test
    void recupererTout_ShouldReturnListOfRecoltes() {
        when(recolteRepository.findAll()).thenReturn(Arrays.asList(recolte));
        when(recolteMapper.toDtoList(any())).thenReturn(Arrays.asList(recolteDTO));

        List<RecolteDTO> recoltes = recolteService.recupererTout();

        assertNotNull(recoltes);
        assertEquals(1, recoltes.size());
    }

    @Test
    void recupererParSaison_ShouldReturnListOfRecoltes_WhenExists() {
        when(recolteRepository.findBySaison(Saison.PRINTEMPS)).thenReturn(Arrays.asList(recolte));
        when(recolteMapper.toDtoList(any())).thenReturn(Arrays.asList(recolteDTO));

        List<RecolteDTO> recoltes = recolteService.recupererParSaison(Saison.PRINTEMPS);

        assertNotNull(recoltes);
        assertEquals(1, recoltes.size());
    }
}

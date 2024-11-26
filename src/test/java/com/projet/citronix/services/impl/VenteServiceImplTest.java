package com.projet.citronix.services.impl;

import com.projet.citronix.dtos.VenteDTO;
import com.projet.citronix.exceptions.VenteException;
import com.projet.citronix.mapper.VenteMapper;
import com.projet.citronix.models.entities.Recolte;
import com.projet.citronix.models.entities.Vente;
import com.projet.citronix.repositories.RecolteRepository;
import com.projet.citronix.repositories.VenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VenteServiceImplTest {

    @Mock
    private VenteRepository venteRepository;

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private VenteMapper venteMapper;

    @InjectMocks
    private VenteServiceImpl venteService;

    private VenteDTO venteDTO;
    private Vente vente;
    private Recolte recolte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        venteDTO = new VenteDTO(1L, new Date(), 100.0, "Client Test", 1L, null);
        vente = new Vente(1L, new Date(), 100.0, "Client Test", null);
        recolte = new Recolte(1L, new Date(), 0.0, null, null, null);
    }

    @Test
    void creer_ShouldCreateVente_WhenValid() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
        when(venteMapper.toEntity(venteDTO)).thenReturn(vente);
        when(venteRepository.save(any())).thenReturn(vente);
        when(venteMapper.toDto(any())).thenReturn(venteDTO);

        VenteDTO createdVente = venteService.creer(venteDTO);

        assertNotNull(createdVente);
        assertEquals(venteDTO.getClient(), createdVente.getClient());
        verify(venteRepository).save(any());
    }

    @Test
    void creer_ShouldThrowException_WhenRecolteNotFound() {
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(VenteException.VenteInexistanteException.class, () -> {
            venteService.creer(venteDTO);
        });
    }

    @Test
    void modifier_ShouldUpdateVente_WhenValid() {
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));
        when(recolteRepository.findById(1L)).thenReturn(Optional.of(recolte));
        when(venteMapper.toEntity(venteDTO)).thenReturn(vente);
        when(venteRepository.save(any())).thenReturn(vente);
        when(venteMapper.toDto(any())).thenReturn(venteDTO);

        VenteDTO updatedVente = venteService.modifier(1L, venteDTO);

        assertNotNull(updatedVente);
        assertEquals(venteDTO.getClient(), updatedVente.getClient());
        verify(venteRepository).save(any());
    }

    @Test
    void modifier_ShouldThrowException_WhenVenteNotFound() {
        when(venteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VenteException.VenteInexistanteException.class, () -> {
            venteService.modifier(1L, venteDTO);
        });
    }

    @Test
    void recupererParId_ShouldReturnVente_WhenExists() {
        when(venteRepository.findById(1L)).thenReturn(Optional.of(vente));
        when(venteMapper.toDto(any())).thenReturn(venteDTO);

        VenteDTO foundVente = venteService.recupererParId(1L);

        assertNotNull(foundVente);
        assertEquals(venteDTO.getId(), foundVente.getId());
    }

    @Test
    void recupererParId_ShouldThrowException_WhenNotFound() {
        when(venteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VenteException.VenteInexistanteException.class, () -> {
            venteService.recupererParId(1L);
        });
    }

    @Test
    void supprimer_ShouldDeleteVente_WhenExists() {
        when(venteRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> venteService.supprimer(1L));
        verify(venteRepository).deleteById(1L);
    }

    @Test
    void supprimer_ShouldThrowException_WhenNotFound() {
        when(venteRepository.existsById(1L)).thenReturn(false);

        assertThrows(VenteException.VenteInexistanteException.class, () -> {
            venteService.supprimer(1L);
        });
    }

    @Test
    void recupererTout_ShouldReturnListOfVentes() {
        when(venteRepository.findAll()).thenReturn(Arrays.asList(vente));
        when(venteMapper.toDtoList(any())).thenReturn(Arrays.asList(venteDTO));

        List<VenteDTO> ventes = venteService.recupererTout();

        assertNotNull(ventes);
        assertEquals(1, ventes.size());
    }

    @Test
    void recupererParRecolte_ShouldReturnListOfVentes_WhenExists() {
        when(venteRepository.findByRecolteId(1L)).thenReturn(Arrays.asList(vente));
        when(venteMapper.toDtoList(any())).thenReturn(Arrays.asList(venteDTO));

        List<VenteDTO> ventes = venteService.recupererParRecolte(1L);

        assertNotNull(ventes);
        assertEquals(1, ventes.size());
    }
}

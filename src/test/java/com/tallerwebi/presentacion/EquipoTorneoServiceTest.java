package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.model.entities.FormatoTorneo;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import com.tallerwebi.dominio.repository.TorneoRepository;
import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.EquipoTorneoServiceImpl;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class EquipoTorneoServiceTest {

   @Mock
   private EquipoTorneoRepository equipoTorneoRepository;

   @Mock
   private TorneoRepository torneoRepository;

   @Mock
   private EquipoRepository equipoRepository;

   private EquipoTorneoService equipoTorneoService;

   private TorneoService torneoService;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
      equipoTorneoService = new EquipoTorneoServiceImpl(equipoTorneoRepository, torneoRepository, equipoRepository,
            torneoService);
   }

   @Test
   public void DadoUnTorneoValidoDebeObtenerListaDeEquiposInscritos() {
      // Preparación
      Long torneoId = 1L;
      List<EquipoTorneo> equipoTorneoList = new ArrayList<>();
      Equipo equipo = new Equipo();
      equipo.setId(1L);
      Torneo torneo = new Torneo();
      torneo.setId(torneoId);
      FormatoTorneo formato = new FormatoTorneo();
      formato.setTipo(TipoFormato.LIGA);
      torneo.setFormatoTorneo(formato);
      torneo.setCapacidadMaxima(20); // Para test de capacidad máxima
      EquipoTorneo equipoTorneo = new EquipoTorneo();
      equipoTorneo.setEquipo(equipo);
      equipoTorneo.setTorneo(torneo);
      equipoTorneoList.add(equipoTorneo);
      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(equipoTorneoList);

      // Ejecución
      List<EquipoTorneoDTO> resultado = equipoTorneoService.getAllByTorneoId(torneoId);

      // Validación
      assertThat(resultado, is(not(empty())));
      assertThat(resultado.size(), is(1));
      verify(equipoTorneoRepository, times(1)).getAllByTorneoId(torneoId);
   }

   @Test
   public void DadoUnTorneoInexistenteDebeRetornarListaVacia() {
      // Preparación
      Long torneoId = 1L;
      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(Collections.emptyList());

      // Ejecución
      List<EquipoTorneoDTO> resultado = equipoTorneoService.getAllByTorneoId(torneoId);

      // Validación
      assertThat(resultado, is(empty()));
      verify(equipoTorneoRepository, times(1)).getAllByTorneoId(torneoId);
   }

   @Test
   public void DadoUnTorneoYEquipoValidosDebeUnirseAlTorneoConExito() {
      // Preparación
      Long torneoId = 1L;
      Long equipoId = 1L;
      Torneo torneo = new Torneo();
      torneo.setId(torneoId);
      FormatoTorneo formato = new FormatoTorneo();
      formato.setTipo(TipoFormato.LIGA);
      torneo.setFormatoTorneo(formato);
      torneo.setCapacidadMaxima(20); // Para test de capacidad máxima
      Equipo equipo = new Equipo();
      equipo.setId(equipoId);
      when(torneoRepository.getById(torneoId)).thenReturn(torneo);

      when(torneoRepository.existsById(torneoId)).thenReturn(true);
      when(equipoRepository.existsById(equipoId)).thenReturn(true);

      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(new ArrayList<>());

      // Ejecución
      equipoTorneoService.unirseTorneo(torneoId, equipoId);

      // Validación
      verify(torneoRepository, times(1)).getById(torneoId);
      verify(equipoTorneoRepository, times(3)).getAllByTorneoId(torneoId);
      verify(equipoTorneoRepository, times(1)).unirEquipoATorneo(equipoId, torneoId);
   }

   @Test
   public void DadoUnTorneoInexistenteDebeLanzarExcepcionAlUnirse() {
      // Preparación
      Long torneoId = 1L;
      Long equipoId = 1L;
      when(torneoRepository.existsById(torneoId)).thenReturn(false);

      // Ejecución y Validación
      try {
         equipoTorneoService.unirseTorneo(torneoId, equipoId);
      } catch (IllegalArgumentException e) {
         assertThat(e.getMessage(), is("El torneo o equipo asociado no pueden ser nulos o no existen"));
      }
   }

   @Test
   public void DadoUnEquipoInexistenteDebeLanzarExcepcionAlUnirse() {
      // Preparación
      Long torneoId = 1L;
      Long equipoId = 1L;
      when(torneoRepository.existsById(torneoId)).thenReturn(true);
      when(equipoRepository.existsById(equipoId)).thenReturn(false);

      // Ejecución y Validación
      try {
         equipoTorneoService.unirseTorneo(torneoId, equipoId);
      } catch (IllegalArgumentException e) {
         assertThat(e.getMessage(), is("El torneo o equipo asociado no pueden ser nulos o no existen"));
      }
   }

   @Test
   public void DadoUnEquipoYaUnidoDebeLanzarExcepcionAlUnirse() {
      // Preparación
      Long torneoId = 1L;
      Long equipoId = 1L;
      Torneo torneo = new Torneo();
      torneo.setId(torneoId);
      FormatoTorneo formato = new FormatoTorneo();
      formato.setTipo(TipoFormato.LIGA);
      torneo.setFormatoTorneo(formato);
      torneo.setCapacidadMaxima(20); // Para test de capacidad máxima
      Equipo equipo = new Equipo();
      equipo.setId(equipoId);
      List<EquipoTorneo> equiposTorneo = new ArrayList<>();
      EquipoTorneo equipoTorneoExistente = new EquipoTorneo();
      equipoTorneoExistente.setEquipo(equipo);
      equipoTorneoExistente.setTorneo(torneo);
      equiposTorneo.add(equipoTorneoExistente);
      when(torneoRepository.existsById(torneoId)).thenReturn(true);
      when(equipoRepository.existsById(equipoId)).thenReturn(true);
      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(equiposTorneo);

      // Ejecución y Validación
      try {
         equipoTorneoService.unirseTorneo(torneoId, equipoId);
      } catch (IllegalArgumentException e) {
         assertThat(e.getMessage(), is("El equipo ya se encuentra unido al torneo"));
      }
   }

   @Test
   public void DadoCapacidadMaximaExcedidaDebeLanzarExcepcionAlUnirse() {
      // Preparación
      Long torneoId = 1L;
      Long equipoId = 1L;
      Torneo torneo = new Torneo();
      torneo.setId(torneoId);
      FormatoTorneo formato = new FormatoTorneo();
      formato.setTipo(TipoFormato.LIGA);
      torneo.setFormatoTorneo(formato);
      torneo.setCapacidadMaxima(20); // Para test de capacidad máxima
      List<EquipoTorneo> equiposTorneo = new ArrayList<>();
      for (int i = 2; i <= 21; i++) {
         Equipo equipo = new Equipo();
         equipo.setId((long) i);
         EquipoTorneo et = new EquipoTorneo();
         et.setEquipo(equipo);
         et.setTorneo(torneo);
         equiposTorneo.add(et);
      }
      when(torneoRepository.getById(torneoId)).thenReturn(torneo);
      when(torneoRepository.existsById(torneoId)).thenReturn(true);
      when(equipoRepository.existsById(equipoId)).thenReturn(true);
      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(equiposTorneo);

      // Ejecución y Validación
      try {
         equipoTorneoService.unirseTorneo(torneoId, equipoId);
      } catch (IllegalArgumentException e) {
         assertThat(e.getMessage(), is("El torneo ya tiene el maximo de equipos permitidos"));

      }
   }

   @Test
   public void DadoTorneoCopaConCapacidadMaximaExcedidaDebeLanzarExcepcionAlUnirse() {
      // Preparación
      Long torneoId = 1L;
      Long equipoId = 1L;
      Torneo torneo = new Torneo();
      torneo.setId(torneoId);
      FormatoTorneo formato = new FormatoTorneo();
      formato.setTipo(TipoFormato.COPA);
      torneo.setFormatoTorneo(formato);
      torneo.setCapacidadMaxima(32); // Para test de capacidad máxima
      List<EquipoTorneo> equiposTorneo = new ArrayList<>();
      for (int i = 2; i <= 33; i++) {
         Equipo equipo = new Equipo();
         equipo.setId((long) i);
         EquipoTorneo et = new EquipoTorneo();
         et.setEquipo(equipo);
         et.setTorneo(torneo);
         equiposTorneo.add(et);
      }
      when(torneoRepository.getById(torneoId)).thenReturn(torneo);
      when(torneoRepository.existsById(torneoId)).thenReturn(true);
      when(equipoRepository.existsById(equipoId)).thenReturn(true);
      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(equiposTorneo);

      // Ejecución y Validación
      try {
         equipoTorneoService.unirseTorneo(torneoId, equipoId);
      } catch (IllegalArgumentException e) {
         assertThat(e.getMessage(), is("El torneo ya tiene el maximo de equipos permitidos"));

      }
   }
}
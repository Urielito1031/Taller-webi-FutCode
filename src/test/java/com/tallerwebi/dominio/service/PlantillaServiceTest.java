package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.presentacion.dto.EsquemaDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PlantillaServiceTest {

  @Mock
  private FormacionEquipoRepository formacionEquipoRepository;

  @Mock
  private EquipoRepository equipoRepository;

  @InjectMocks
  private PlantillaServiceImpl plantillaService;

  private Long equipoId;
  private List<FormacionEquipo> formaciones;
  private EsquemaDTO esquemaDTO;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    equipoId = 1L;
    formaciones = new ArrayList<>();
    esquemaDTO = new EsquemaDTO();
    esquemaDTO.setEquipoId(equipoId);
  }

  @Test
  public void deberiaInicializarPlantillaBaseConFormacionesExistentes() {
    // Preparación
    List<FormacionEquipo> formacionesExistentes = crearFormacionesDePrueba();
    when(formacionEquipoRepository.findByEquipoId(equipoId)).thenReturn(formacionesExistentes);

    // Ejecución
    EsquemaDTO resultado = plantillaService.initPlantillaBase(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getEquipoId(), is(equipoId));
    assertThat(resultado.getAlineacion(), notNullValue());
    assertThat(resultado.getAlineacion(), hasSize(11));
    assertThat(resultado.getEsquema(), is(FormacionEsquema.CUATRO_TRES_TRES));
    verify(formacionEquipoRepository).findByEquipoId(equipoId);
  }

  @Test
  public void deberiaInicializarPlantillaBaseSinFormacionesExistentes() {
    // Preparación
    when(formacionEquipoRepository.findByEquipoId(equipoId)).thenReturn(new ArrayList<>());

    // Ejecución
    EsquemaDTO resultado = plantillaService.initPlantillaBase(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getEquipoId(), is(equipoId));
    assertThat(resultado.getAlineacion(), notNullValue());
    assertThat(resultado.getAlineacion(), hasSize(0));
    verify(formacionEquipoRepository).findByEquipoId(equipoId);
  }

  @Test
  public void deberiaGuardarFormacionExitosamente() {
    // Preparación
    esquemaDTO.setAlineacion(crearAlineacionDePrueba());
    when(equipoRepository.existsById(equipoId)).thenReturn(true);
    doNothing().when(formacionEquipoRepository).save(any(FormacionEquipo.class));

    // Ejecución
    Boolean resultado = plantillaService.save(esquemaDTO);

    // Verificación
    assertThat(resultado, is(true));
    verify(equipoRepository).existsById(equipoId);
    verify(formacionEquipoRepository).deleteByEquipoId(equipoId);
    verify(formacionEquipoRepository, times(11)).save(any(FormacionEquipo.class));
  }

  @Test
  public void deberiaRetornarFalseSiEquipoNoExiste() {
    // Preparación
    esquemaDTO.setAlineacion(crearAlineacionDePrueba());
    when(equipoRepository.existsById(equipoId)).thenReturn(false);

    // Ejecución
    Boolean resultado = plantillaService.save(esquemaDTO);

    // Verificación
    assertThat(resultado, is(false));
    verify(equipoRepository).existsById(equipoId);
    verify(formacionEquipoRepository, never()).deleteByEquipoId(anyLong());
    verify(formacionEquipoRepository, never()).save(any(FormacionEquipo.class));
  }

  @Test
  public void deberiaRetornarFalseSiEquipoIdEsNull() {
    // Preparación
    esquemaDTO.setEquipoId(null);
    esquemaDTO.setAlineacion(crearAlineacionDePrueba());

    // Ejecución
    Boolean resultado = plantillaService.save(esquemaDTO);

    // Verificación
    assertThat(resultado, is(false));
    verify(equipoRepository, never()).existsById(anyLong());
    verify(formacionEquipoRepository, never()).deleteByEquipoId(anyLong());
    verify(formacionEquipoRepository, never()).save(any(FormacionEquipo.class));
  }

  @Test
  public void deberiaRetornarFalseSiEquipoIdEsCero() {
    // Preparación
    esquemaDTO.setEquipoId(0L);
    esquemaDTO.setAlineacion(crearAlineacionDePrueba());

    // Ejecución
    Boolean resultado = plantillaService.save(esquemaDTO);

    // Verificación
    assertThat(resultado, is(false));
    verify(equipoRepository, never()).existsById(anyLong());
    verify(formacionEquipoRepository, never()).deleteByEquipoId(anyLong());
    verify(formacionEquipoRepository, never()).save(any(FormacionEquipo.class));
  }

  @Test
  public void deberiaLanzarExcepcionSiFormacionEsNull() {
    // Preparación
    esquemaDTO = null;

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      plantillaService.save(esquemaDTO);
    });

    assertThat(exception.getMessage(), is("Formación inválida: datos nulos."));
  }

  @Test
  public void deberiaLanzarExcepcionSiAlineacionEsNull() {
    // Preparación
    esquemaDTO.setAlineacion(null);

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      plantillaService.save(esquemaDTO);
    });

    assertThat(exception.getMessage(), is("Formación inválida: datos nulos."));
  }

  @Test
  public void deberiaLanzarExcepcionSiAlineacionNoTiene11Jugadores() {
    // Preparación
    List<PosicionJugadorDTO> alineacionIncompleta = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      alineacionIncompleta.add(new PosicionJugadorDTO());
    }
    esquemaDTO.setAlineacion(alineacionIncompleta);

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      plantillaService.save(esquemaDTO);
    });

    assertThat(exception.getMessage(), is("La formación debe tener exactamente 11 jugadores."));
  }

  @Test
  public void deberiaObtenerFormacionPorEquipoId() {
    // Preparación
    List<FormacionEquipo> formacionesExistentes = crearFormacionesDePrueba();
    when(formacionEquipoRepository.findByEquipoId(equipoId)).thenReturn(formacionesExistentes);

    // Ejecución
    EsquemaDTO resultado = plantillaService.getFormacionPorEquipoId(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getEquipoId(), is(equipoId));
    assertThat(resultado.getAlineacion(), notNullValue());
    assertThat(resultado.getAlineacion(), hasSize(11));
    assertThat(resultado.getEsquema(), is(FormacionEsquema.CUATRO_TRES_TRES));
    verify(formacionEquipoRepository).findByEquipoId(equipoId);
  }

  @Test
  public void deberiaObtenerFormacionPorEquipoIdSinFormacionesExistentes() {
    // Preparación
    when(formacionEquipoRepository.findByEquipoId(equipoId)).thenReturn(new ArrayList<>());

    // Ejecución
    EsquemaDTO resultado = plantillaService.getFormacionPorEquipoId(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getEquipoId(), is(equipoId));
    assertThat(resultado.getAlineacion(), notNullValue());
    assertThat(resultado.getAlineacion(), hasSize(0));
    assertThat(resultado.getEsquema(), is(FormacionEsquema.CUATRO_TRES_TRES));
    verify(formacionEquipoRepository).findByEquipoId(equipoId);
  }

  private List<FormacionEquipo> crearFormacionesDePrueba() {
    List<FormacionEquipo> formaciones = new ArrayList<>();

    // Crear 11 jugadores para una formación 4-3-3
    for (int i = 1; i <= 11; i++) {
      FormacionEquipo formacion = new FormacionEquipo();
      Jugador jugador = new Jugador();
      jugador.setId((long) i);
      jugador.setNombre("Jugador " + i);
      formacion.setJugador(jugador);

      // Asignar posiciones según 4-3-3
      if (i <= 4) {
        formacion.setPosicionEnCampo(PosicionEnum.DEFENSOR);
      } else if (i <= 7) {
        formacion.setPosicionEnCampo(PosicionEnum.MEDIOCAMPISTA);
      } else {
        formacion.setPosicionEnCampo(PosicionEnum.DELANTERO);
      }

      formaciones.add(formacion);
    }

    return formaciones;
  }

  private List<PosicionJugadorDTO> crearAlineacionDePrueba() {
    List<PosicionJugadorDTO> alineacion = new ArrayList<>();

    // Crear 11 jugadores para una formación 4-3-3
    for (int i = 1; i <= 11; i++) {
      PosicionJugadorDTO posicion = new PosicionJugadorDTO();
      posicion.setJugadorId((long) i);

      // Asignar posiciones según 4-3-3
      if (i <= 4) {
        posicion.setPosicionEnCampo(PosicionEnum.DEFENSOR);
      } else if (i <= 7) {
        posicion.setPosicionEnCampo(PosicionEnum.MEDIOCAMPISTA);
      } else {
        posicion.setPosicionEnCampo(PosicionEnum.DELANTERO);
      }

      alineacion.add(posicion);
    }

    return alineacion;
  }
}
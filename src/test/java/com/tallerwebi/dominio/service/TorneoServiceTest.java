package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import com.tallerwebi.dominio.repository.TorneoRepository;
import com.tallerwebi.presentacion.dto.TorneoDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TorneoServiceTest {

  @Mock
  private TorneoRepository torneoRepository;

  @Mock
  private EquipoTorneoRepository equipoTorneoRepository;

  @InjectMocks
  private TorneoServiceImpl torneoService;

  private Torneo torneo;
  private List<Torneo> torneos;
  private List<Equipo> equipos;
  private List<Partido> partidos;
  private List<EquipoTorneo> equiposTorneo;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Configurar datos de prueba
    torneo = new Torneo();
    torneo.setId(1L);
    torneo.setNombre("Torneo Test");
    torneo.setDescripcion("Descripción del torneo");
    torneo.setEstado(EstadoTorneoEnum.ABIERTO);

    FormatoTorneo formato = new FormatoTorneo();
    formato.setId(1L);
    formato.setTipo(TipoFormato.LIGA);
    torneo.setFormatoTorneo(formato);

    torneos = Arrays.asList(torneo);

    // Crear equipos de prueba
    equipos = new ArrayList<>();
    for (int i = 1; i <= 4; i++) {
      Equipo equipo = new Equipo();
      equipo.setId((long) i);
      equipo.setNombre("Equipo " + i);
      equipos.add(equipo);
    }

    // Crear partidos de prueba
    partidos = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      Partido partido = new Partido();
      partido.setId((long) (i + 1));
      partido.setEquipoLocal(equipos.get(i));
      partido.setEquipoVisitante(equipos.get(i + 2));
      partido.setResultado(ResultadoPartido.LOCAL_GANA);
      partido.setGolesLocal(2);
      partido.setGolesVisitante(1);
      partidos.add(partido);
    }

    // Crear equipos torneo de prueba
    equiposTorneo = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      EquipoTorneo equipoTorneo = new EquipoTorneo();
      equipoTorneo.setEquipo(equipos.get(i));
      equipoTorneo.setTorneo(torneo);
      equipoTorneo.setPuntos(0);
      equipoTorneo.setPartidosJugados(0);
      equiposTorneo.add(equipoTorneo);
    }
  }

  @Test
  public void deberiaObtenerTodosLosTorneos() {
    // Preparación
    when(torneoRepository.findAll()).thenReturn(torneos);

    // Ejecución
    List<TorneoDTO> resultado = torneoService.getAll();

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(1));
    assertThat(resultado.get(0).getNombre(), is("Torneo Test"));
    verify(torneoRepository).findAll();
  }

  @Test
  public void deberiaObtenerTorneoPorId() {
    // Preparación
    when(torneoRepository.getById(1L)).thenReturn(torneo);

    // Ejecución
    TorneoDTO resultado = torneoService.getById(1L);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getNombre(), is("Torneo Test"));
    verify(torneoRepository).getById(1L);
  }

  @Test
  public void deberiaRetornarNullSiTorneoNoExiste() {
    // Preparación
    when(torneoRepository.getById(999L)).thenReturn(null);

    // Ejecución
    TorneoDTO resultado = torneoService.getById(999L);

    // Verificación
    assertThat(resultado, nullValue());
    verify(torneoRepository).getById(999L);
  }

  @Test
  public void deberiaObtenerTorneoConFechas() {
    // Preparación
    when(torneoRepository.obtenerTorneoConFechas(1L)).thenReturn(torneo);

    // Ejecución
    Torneo resultado = torneoService.obtenerTorneoConFechas(1L);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getNombre(), is("Torneo Test"));
    verify(torneoRepository).obtenerTorneoConFechas(1L);
  }

  @Test
  public void deberiaCalcularTablaDePosiciones() {
    // Preparación

    // Ejecución
    List<EquipoTorneo> resultado = torneoService.calcularTablaDePosiciones(partidos, equiposTorneo);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(4));

    // Verificar que se guardaron los equipos torneo
    // 2 partidos * 2 equipos por partido = 4 llamadas a save
    verify(equipoTorneoRepository, times(4)).save(any(EquipoTorneo.class));
  }

  @Test
  public void deberiaGenerarFechasParaEquiposPares() {
    // Preparación
    List<Equipo> equiposPares = equipos.subList(0, 4); // 4 equipos

    // Ejecución
    List<Fecha> resultado = torneoService.generarFechas(equiposPares, torneo);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(3)); // Para 4 equipos: 3 fechas
    assertThat(resultado.get(0).getNumeroDeFecha(), is(1L));
    assertThat(resultado.get(1).getNumeroDeFecha(), is(2L));
    assertThat(resultado.get(2).getNumeroDeFecha(), is(3L));

    // Verificar que cada fecha tiene partidos
    for (Fecha fecha : resultado) {
      assertThat(fecha.getPartidos(), notNullValue());
      assertThat(fecha.getPartidos(), hasSize(2)); // 2 partidos por fecha
    }
  }

  @Test
  public void deberiaGenerarFechasParaEquiposImpares() {
    // Preparación
    List<Equipo> equiposImpares = equipos.subList(0, 3); // 3 equipos
    equiposImpares.add(new Equipo()); // Agregar equipo null para completar

    // Ejecución
    List<Fecha> resultado = torneoService.generarFechas(equiposImpares, torneo);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(3)); // Para 4 equipos (con null): 3 fechas
  }

  @Test
  public void deberiaCrearFixtureConLasFechas() {
    // Preparación
    when(torneoRepository.getById(1L)).thenReturn(torneo);
    doNothing().when(torneoRepository).save(any(Torneo.class));

    // Ejecución
    torneoService.crearFixtureConLasFechas(1L);

    // Verificación
    verify(torneoRepository).getById(1L);
    verify(torneoRepository).save(any(Torneo.class));
  }

  @Test
  public void deberiaCalcularTablaDePosicionesConPartidosEmpatados() {
    // Preparación
    List<Partido> partidosEmpatados = new ArrayList<>();
    Partido partidoEmpate = new Partido();
    partidoEmpate.setEquipoLocal(equipos.get(0));
    partidoEmpate.setEquipoVisitante(equipos.get(1));
    partidoEmpate.setResultado(ResultadoPartido.EMPATE);
    partidoEmpate.setGolesLocal(1);
    partidoEmpate.setGolesVisitante(1);
    partidosEmpatados.add(partidoEmpate);

    // Ejecución
    List<EquipoTorneo> resultado = torneoService.calcularTablaDePosiciones(partidosEmpatados, equiposTorneo);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(4));
    verify(equipoTorneoRepository, times(2)).save(any(EquipoTorneo.class)); // 2 equipos * 1 partido
  }

  @Test
  public void deberiaCalcularTablaDePosicionesConPartidosVisitanteGana() {
    // Preparación
    List<Partido> partidosVisitanteGana = new ArrayList<>();
    Partido partidoVisitante = new Partido();
    partidoVisitante.setEquipoLocal(equipos.get(0));
    partidoVisitante.setEquipoVisitante(equipos.get(1));
    partidoVisitante.setResultado(ResultadoPartido.VISITANTE_GANA);
    partidoVisitante.setGolesLocal(0);
    partidoVisitante.setGolesVisitante(2);
    partidosVisitanteGana.add(partidoVisitante);

    // Ejecución
    List<EquipoTorneo> resultado = torneoService.calcularTablaDePosiciones(partidosVisitanteGana, equiposTorneo);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(4));
    verify(equipoTorneoRepository, times(2)).save(any(EquipoTorneo.class)); // 2 equipos * 1 partido
  }

  @Test
  public void deberiaIgnorarPartidosPendientesEnCalculoTabla() {
    // Preparación
    List<Partido> partidosConPendientes = new ArrayList<>();
    Partido partidoPendiente = new Partido();
    partidoPendiente.setEquipoLocal(equipos.get(0));
    partidoPendiente.setEquipoVisitante(equipos.get(1));
    partidoPendiente.setResultado(ResultadoPartido.PENDIENTE);
    partidosConPendientes.add(partidoPendiente);

    // Ejecución
    List<EquipoTorneo> resultado = torneoService.calcularTablaDePosiciones(partidosConPendientes, equiposTorneo);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(4));
    verify(equipoTorneoRepository, never()).save(any(EquipoTorneo.class)); // No se guardan equipos porque no hay
                                                                           // partidos válidos
  }
}
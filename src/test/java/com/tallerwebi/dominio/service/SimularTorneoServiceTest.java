package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.dominio.service.SimularTorneoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SimularTorneoServiceTest {

  @Mock
  private PartidoRepository partidoRepository;

  @Mock
  private TorneoRepository torneoRepository;

  @Mock
  private FechaRepository fechaRepository;

  @Mock
  private FrasePartidoService frasePartidoService;

  @Mock
  private NarracionRepository narracionRepository;

  @Mock
  private JugadorService jugadorService;

  @Mock
  private JugadorRepository jugadorRepository;

  @InjectMocks
  private SimularTorneoServiceImpl simularTorneoService;

  private Torneo torneo;
  private Fecha fecha;
  private List<Partido> partidos;
  private Equipo equipoLocal;
  private Equipo equipoVisitante;
  private Partido partido;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Configurar datos de prueba
    torneo = new Torneo();
    torneo.setId(1L);
    torneo.setNombre("Torneo Test");

    fecha = new Fecha();
    fecha.setId(1L);
    fecha.setNumeroDeFecha(1L);
    fecha.setTorneo(torneo);
    fecha.setSimulada(false);

    equipoLocal = new Equipo();
    equipoLocal.setId(1L);
    equipoLocal.setNombre("Equipo Local");

    equipoVisitante = new Equipo();
    equipoVisitante.setId(2L);
    equipoVisitante.setNombre("Equipo Visitante");

    partido = new Partido();
    partido.setId(1L);
    partido.setEquipoLocal(equipoLocal);
    partido.setEquipoVisitante(equipoVisitante);
    partido.setFecha(fecha);
    partido.setGolesLocal(0);
    partido.setGolesVisitante(0);

    partidos = new ArrayList<>();
    partidos.add(partido);
    fecha.setPartidos(partidos);

    Set<Fecha> fechas = new HashSet<>();
    fechas.add(fecha);
    torneo.setFechas(fechas);
  }

  @Test
  public void deberiaSimularFechaExitosamente() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
    when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha)).thenReturn(fecha);
    when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
        .thenReturn("Frase de prueba");

    // Ejecución
    simularTorneoService.simularFecha(torneoId, numeroDeFecha);

    // Verificación
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verify(fechaRepository).getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
    verify(jugadorService, times(2)).asegurarJugadoresIniciales(any());
    verify(partidoRepository).save(partido);
    verify(fechaRepository).save(fecha);

    assertThat(partido.getGolesLocal(), greaterThanOrEqualTo(0));
    assertThat(partido.getGolesVisitante(), greaterThanOrEqualTo(0));
    assertThat(partido.getResultado(), notNullValue());
    assertThat(fecha.isSimulada(), is(true));
  }

  @Test
  public void deberiaLanzarExcepcionSiTorneoNoExiste() {
    // Preparación
    Long torneoId = 999L;
    Long numeroDeFecha = 1L;

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(null);

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      simularTorneoService.simularFecha(torneoId, numeroDeFecha);
    });

    assertThat(exception.getMessage(), is("Torneo no encontrado o sin fechas"));
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verifyNoInteractions(fechaRepository);
    verifyNoInteractions(partidoRepository);
  }

  @Test
  public void deberiaLanzarExcepcionSiTorneoNoTieneFechas() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;

    Torneo torneoSinFechas = new Torneo();
    torneoSinFechas.setId(torneoId);
    torneoSinFechas.setFechas(new HashSet<>());

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneoSinFechas);

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      simularTorneoService.simularFecha(torneoId, numeroDeFecha);
    });

    assertThat(exception.getMessage(), is("Torneo no encontrado o sin fechas"));
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verifyNoInteractions(fechaRepository);
    verifyNoInteractions(partidoRepository);
  }

  @Test
  public void deberiaSimularFechaYDevolverPrimerPartido() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;
    Long partidoId = 1L;

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
    when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha)).thenReturn(fecha);
    when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
        .thenReturn("Frase de prueba");

    // Ejecución
    Long resultado = simularTorneoService.simularFechaYDevolverPrimerPartido(torneoId, numeroDeFecha);

    // Verificación
    assertThat(resultado, is(partidoId));
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verify(fechaRepository, times(2)).getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
    verify(partidoRepository).save(partido);
    verify(fechaRepository).save(fecha);
  }

  @Test
  public void deberiaDevolverNullSiFechaNoTienePartidos() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;

    Fecha fechaSinPartidos = new Fecha();
    fechaSinPartidos.setId(1L);
    fechaSinPartidos.setNumeroDeFecha(1L);
    fechaSinPartidos.setPartidos(new ArrayList<>());

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
    when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha)).thenReturn(fechaSinPartidos);
    when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
        .thenReturn("Frase de prueba");

    // Ejecución
    Long resultado = simularTorneoService.simularFechaYDevolverPrimerPartido(torneoId, numeroDeFecha);

    // Verificación
    assertThat(resultado, nullValue());
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verify(fechaRepository, times(2)).getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
  }

  @Test
  public void deberiaObtenerPartidoSimulado() {
    // Preparación
    Long partidoId = 1L;

    when(partidoRepository.obtenerPartidoPorId(partidoId)).thenReturn(partido);

    // Ejecución
    Partido resultado = simularTorneoService.obtenerPartidoSimulado(partidoId);

    // Verificación
    assertThat(resultado, is(partido));
    verify(partidoRepository).obtenerPartidoPorId(partidoId);
  }

  @Test
  public void deberiaObtenerFechaConPartidos() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;

    when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha)).thenReturn(fecha);

    // Ejecución
    Fecha resultado = simularTorneoService.obtenerFechaConPartidos(torneoId, numeroDeFecha);

    // Verificación
    assertThat(resultado, is(fecha));
    verify(fechaRepository).getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
  }

  @Test
  public void deberiaSimularPartidoConEquiposConJugadores() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;

    // Agregar jugadores a los equipos
    Jugador jugador1 = new Jugador();
    jugador1.setId(1L);
    jugador1.setRating(80.0);
    equipoLocal.addJugador(jugador1);

    Jugador jugador2 = new Jugador();
    jugador2.setId(2L);
    jugador2.setRating(75.0);
    equipoVisitante.addJugador(jugador2);

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
    when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha)).thenReturn(fecha);
    when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
        .thenReturn("Frase de prueba");

    // Ejecución
    simularTorneoService.simularFecha(torneoId, numeroDeFecha);

    // Verificación
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verify(fechaRepository).getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
    verify(jugadorService, times(2)).asegurarJugadoresIniciales(any());
    verify(partidoRepository).save(partido);
    verify(fechaRepository).save(fecha);

    // Verificar que se generaron frases para los goles
    verify(frasePartidoService, atLeastOnce()).generarFraseConJugadorAleatorio(eq(EventoPartido.GOL),
        eq(equipoLocal.getId()));
    verify(frasePartidoService, atLeastOnce()).generarFraseConJugadorAleatorio(eq(EventoPartido.GOL),
        eq(equipoVisitante.getId()));

    assertThat(partido.getGolesLocal(), greaterThanOrEqualTo(0));
    assertThat(partido.getGolesVisitante(), greaterThanOrEqualTo(0));
    assertThat(partido.getResultado(), notNullValue());
  }

  @Test
  public void deberiaManejarExcepcionEnGeneracionDeFrases() {
    // Preparación
    Long torneoId = 1L;
    Long numeroDeFecha = 1L;

    when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
    when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha)).thenReturn(fecha);
    when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
        .thenThrow(new RuntimeException("Error generando frase"));

    // Ejecución (no debe lanzar excepción)
    simularTorneoService.simularFecha(torneoId, numeroDeFecha);

    // Verificación
    verify(torneoRepository).obtenerTorneoConFechas(torneoId);
    verify(fechaRepository).getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
    verify(partidoRepository).save(partido);
    verify(fechaRepository).save(fecha);

    // Verificar que se intentó generar frases
    verify(frasePartidoService, atLeastOnce()).generarFraseConJugadorAleatorio(any(), anyLong());
  }

  // @Test
  // public void deberiaSimularPartidoConResultadoLocalGana() {
  // // Preparación
  // Long torneoId = 1L;
  // Long numeroDeFecha = 1L;
  //
  // when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
  // when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId,
  // numeroDeFecha)).thenReturn(fecha);
  // when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
  // .thenReturn("Frase de prueba");
  //
  // // Forzar resultado específico
  // partido.setGolesLocal(3);
  // partido.setGolesVisitante(1);
  //
  // // Ejecución
  // simularTorneoService.simularFecha(torneoId, numeroDeFecha);
  //
  // // Verificación
  // verify(partidoRepository).save(partido);
  // assertThat(partido.getResultado(), is(ResultadoPartido.LOCAL_GANA));
  // }
  //
  // @Test
  // public void deberiaSimularPartidoConResultadoVisitanteGana() {
  // // Preparación
  // Long torneoId = 1L;
  // Long numeroDeFecha = 1L;
  //
  // when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
  // when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId,
  // numeroDeFecha)).thenReturn(fecha);
  // when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
  // .thenReturn("Frase de prueba");
  //
  // // Forzar resultado específico
  // partido.setGolesLocal(1);
  // partido.setGolesVisitante(3);
  //
  // // Ejecución
  // simularTorneoService.simularFecha(torneoId, numeroDeFecha);
  //
  // // Verificación
  // verify(partidoRepository).save(partido);
  // assertThat(partido.getResultado(), is(ResultadoPartido.VISITANTE_GANA));
  // }

  // @Test
  // public void deberiaSimularPartidoConResultadoEmpate() {
  // // Preparación
  // Long torneoId = 1L;
  // Long numeroDeFecha = 1L;
  //
  // when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneo);
  // when(fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId,
  // numeroDeFecha)).thenReturn(fecha);
  // when(frasePartidoService.generarFraseConJugadorAleatorio(any(), anyLong()))
  // .thenReturn("Frase de prueba");
  //
  // // Forzar resultado específico
  // partido.setGolesLocal(2);
  // partido.setGolesVisitante(2);
  //
  // // Ejecución
  // simularTorneoService.simularFecha(torneoId, numeroDeFecha);
  //
  // // Verificación
  // verify(partidoRepository).save(partido);
  // assertThat(partido.getResultado(), is(ResultadoPartido.EMPATE));
  // }
}
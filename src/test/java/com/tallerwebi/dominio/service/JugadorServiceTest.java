package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class JugadorServiceTest {

  @Mock
  private JugadorRepository jugadorRepository;

  @Mock
  private EquipoRepository equipoRepository;

  @InjectMocks
  private JugadorServiceImpl jugadorService;

  private List<Jugador> jugadores;
  private List<JugadorDTO> jugadoresDTO;
  private EquipoDTO equipoDTO;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Configurar datos de prueba
    jugadores = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      Jugador jugador = new Jugador();
      jugador.setId((long) i);
      jugador.setNombre("Jugador " + i);
      jugador.setApellido("Apellido " + i);
      jugador.setRating(70.0 + i);
      jugador.setPosicion(PosicionEnum.DELANTERO);
      jugador.setRarezaJugador(RarezaJugador.NORMAL);
      jugador.setEdad(25);
      jugador.setNumeroCamiseta(i);
      jugador.setEstadoFisico(90.0);
      jugador.setLesionado(false);
      jugadores.add(jugador);
    }

    jugadoresDTO = new ArrayList<>();
    for (Jugador jugador : jugadores) {
      jugadoresDTO.add(jugador.convertToDTO());
    }

    equipoDTO = new EquipoDTO();
    equipoDTO.setId(1L);
    equipoDTO.setNombre("Equipo Test");
  }

  @Test
  public void deberiaObtenerTodosLosJugadores() {
    // Preparación
    when(jugadorRepository.getAll()).thenReturn(jugadores);

    // Ejecución
    List<JugadorDTO> resultado = jugadorService.getAll();

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(5));
    assertThat(resultado.get(0).getNombre(), is("Jugador 1"));
    assertThat(resultado.get(4).getNombre(), is("Jugador 5"));
    verify(jugadorRepository).getAll();
  }

  @Test
  public void deberiaObtenerJugadoresPorEquipoId() {
    // Preparación
    Long equipoId = 1L;
    when(jugadorRepository.getAllByEquipoId(equipoId)).thenReturn(jugadores);

    // Ejecución
    List<JugadorDTO> resultado = jugadorService.getAllByEquipoId(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(5));
    assertThat(resultado.get(0).getNombre(), is("Jugador 1"));
    verify(jugadorRepository).getAllByEquipoId(equipoId);
  }

  @Test
  public void deberiaRetornarListaVaciaSiNoHayJugadoresEnEquipo() {
    // Preparación
    Long equipoId = 1L;
    when(jugadorRepository.getAllByEquipoId(equipoId)).thenReturn(new ArrayList<>());

    // Ejecución
    List<JugadorDTO> resultado = jugadorService.getAllByEquipoId(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(0));
    verify(jugadorRepository).getAllByEquipoId(equipoId);
  }

  @Test
  public void deberiaRetornarListaVaciaSiJugadoresEsNull() {
    // Preparación
    Long equipoId = 1L;
    when(jugadorRepository.getAllByEquipoId(equipoId)).thenReturn(null);

    // Ejecución
    List<JugadorDTO> resultado = jugadorService.getAllByEquipoId(equipoId);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(0));
    verify(jugadorRepository).getAllByEquipoId(equipoId);
  }

  @Test
  public void deberiaSortearJugadoresIniciales() {
    // Preparación
    List<Jugador> arqueros = Arrays.asList(jugadores.get(0), jugadores.get(1));
    List<Jugador> mediocampistas = Arrays.asList(jugadores.get(2), jugadores.get(3), jugadores.get(4));
    List<Jugador> defensores = Arrays.asList(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3),
        jugadores.get(4));
    List<Jugador> delanteros = Arrays.asList(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3));

    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.ARQUERO, 2))
        .thenReturn(arqueros);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.MEDIOCAMPISTA, 3))
        .thenReturn(mediocampistas);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DEFENSOR, 5))
        .thenReturn(defensores);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DELANTERO, 4))
        .thenReturn(delanteros);

    // Ejecución
    List<Jugador> resultado = jugadorService.sortearJugadoresIniciales(14);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(14)); // 2 + 3 + 5 + 4 = 14 (suma de todos los jugadores sorteados)
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.ARQUERO, 2);
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.MEDIOCAMPISTA, 3);
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DEFENSOR, 5);
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DELANTERO, 4);
  }

  @Test
  public void deberiaCargarJugadoresAlEquipo() {
    // Preparación
    List<Jugador> arqueros = Arrays.asList(jugadores.get(0), jugadores.get(1));
    List<Jugador> mediocampistas = Arrays.asList(jugadores.get(2), jugadores.get(3), jugadores.get(4));
    List<Jugador> defensores = Arrays.asList(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3),
        jugadores.get(4));
    List<Jugador> delanteros = Arrays.asList(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3));

    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.ARQUERO, 2))
        .thenReturn(arqueros);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.MEDIOCAMPISTA, 3))
        .thenReturn(mediocampistas);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DEFENSOR, 5))
        .thenReturn(defensores);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DELANTERO, 4))
        .thenReturn(delanteros);

    // Ejecución
    jugadorService.cargarJugadoresAlEquipo(equipoDTO);

    // Verificación
    assertThat(equipoDTO.getJugadores(), notNullValue());
    assertThat(equipoDTO.getJugadores(), hasSize(14)); // 2 + 3 + 5 + 4 = 14
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.ARQUERO, 2);
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.MEDIOCAMPISTA, 3);
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DEFENSOR, 5);
    verify(jugadorRepository).sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DELANTERO, 4);
  }

  @Test
  public void deberiaAsegurarJugadoresIniciales() {
    // Preparación
    Equipo equipo = new Equipo();
    equipo.setId(1L);
    equipo.setNombre("Equipo Test");
    equipo.setJugadores(new ArrayList<>());

    List<Jugador> arqueros = Arrays.asList(jugadores.get(0), jugadores.get(1));
    List<Jugador> mediocampistas = Arrays.asList(jugadores.get(2), jugadores.get(3), jugadores.get(4));
    List<Jugador> defensores = Arrays.asList(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3),
        jugadores.get(4));
    List<Jugador> delanteros = Arrays.asList(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3));

    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.ARQUERO, 2))
        .thenReturn(arqueros);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.MEDIOCAMPISTA, 3))
        .thenReturn(mediocampistas);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DEFENSOR, 5))
        .thenReturn(defensores);
    when(jugadorRepository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DELANTERO, 4))
        .thenReturn(delanteros);
    doNothing().when(equipoRepository).save(any(Equipo.class));

    // Ejecución
    jugadorService.asegurarJugadoresIniciales(equipo);

    // Verificación
    assertThat(equipo, notNullValue());
    assertThat(equipo.getJugadores(), notNullValue());
    assertThat(equipo.getJugadores(), hasSize(14)); // 2 + 3 + 5 + 4 = 14
    verify(equipoRepository).save(any(Equipo.class));
  }

}
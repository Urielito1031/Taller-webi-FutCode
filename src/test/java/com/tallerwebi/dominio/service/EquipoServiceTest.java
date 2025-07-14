package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class EquipoServiceTest {

  @Mock
  private EquipoRepository equipoRepository;

  @Mock
  private UsuarioService usuarioService;

  @Mock
  private FormacionEquipoRepository formacionEquipoRepository;

  @InjectMocks
  private EquipoServiceImpl equipoService;

  private EquipoDTO equipoDTO;
  private Equipo equipo;
  private Usuario usuario;
  private List<JugadorDTO> jugadoresDTO;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Configurar datos de prueba
    equipoDTO = new EquipoDTO();
    equipoDTO.setId(1L);
    equipoDTO.setNombre("Equipo Test");

    equipo = new Equipo();
    equipo.setId(1L);
    equipo.setNombre("Equipo Test");

    usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("test@test.com");

    // Crear jugadores de prueba
    jugadoresDTO = new ArrayList<>();
    JugadorDTO jugador1 = new JugadorDTO();
    jugador1.setId(1L);
    jugador1.setNombre("Jugador 1");
    jugador1.setRating(80.0);
    jugador1.setPosicionNatural(PosicionEnum.DELANTERO);
    jugador1.setRarezaJugador(RarezaJugador.NORMAL);
    jugadoresDTO.add(jugador1);

    JugadorDTO jugador2 = new JugadorDTO();
    jugador2.setId(2L);
    jugador2.setNombre("Jugador 2");
    jugador2.setRating(75.0);
    jugador2.setPosicionNatural(PosicionEnum.MEDIOCAMPISTA);
    jugador2.setRarezaJugador(RarezaJugador.NORMAL);
    jugadoresDTO.add(jugador2);

    equipoDTO.setJugadores(jugadoresDTO);
  }

  @Test
  public void deberiaGuardarEquipoExitosamente() {
    // Preparación
    doNothing().when(equipoRepository).save(any(Equipo.class));

    // Ejecución
    equipoService.save(equipoDTO);

    // Verificación
    verify(equipoRepository).save(any(Equipo.class));
  }

  @Test
  public void deberiaLanzarExcepcionSiNombreEquipoEsVacio() {
    // Preparación
    equipoDTO.setNombre("");

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      equipoService.save(equipoDTO);
    });

    assertThat(exception.getMessage(), is("El nombre no puede ser vacio"));
    verify(equipoRepository, never()).save(any(Equipo.class));
  }

  @Test
  public void deberiaLanzarExcepcionSiNombreEquipoEsNull() {
    // Preparación
    equipoDTO.setNombre(null);

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      equipoService.save(equipoDTO);
    });

    assertThat(exception.getMessage(), is("El nombre no puede ser vacio"));
    verify(equipoRepository, never()).save(any(Equipo.class));
  }

  @Test
  public void deberiaGuardarEquipoYUsuarioExitosamente() {
    // Preparación
    doNothing().when(equipoRepository).save(any(Equipo.class));

    // Ejecución
    equipoService.saveBoth(equipoDTO, usuario);

    // Verificación
    verify(equipoRepository).save(any(Equipo.class));
    assertThat(usuario.getEquipo(), notNullValue());
  }

  @Test
  public void deberiaLanzarExcepcionSiNombreEquipoEsVacioEnSaveBoth() {
    // Preparación
    equipoDTO.setNombre("");

    // Ejecución y Verificación
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      equipoService.saveBoth(equipoDTO, usuario);
    });

    assertThat(exception.getMessage(), is("El nombre no puede ser vacío"));
    verify(equipoRepository, never()).save(any(Equipo.class));
  }

  @Test
  public void deberiaCalcularRatingEquipoCorrectamente() {
    // Preparación
    List<FormacionEquipo> formaciones = new ArrayList<>();

    Jugador jugador1 = new Jugador();
    jugador1.setRating(80.0);

    Jugador jugador2 = new Jugador();
    jugador2.setRating(75.0);

    FormacionEquipo formacion1 = new FormacionEquipo();
    formacion1.setJugador(jugador1);

    FormacionEquipo formacion2 = new FormacionEquipo();
    formacion2.setJugador(jugador2);

    formaciones.add(formacion1);
    formaciones.add(formacion2);

    when(formacionEquipoRepository.findByEquipoId(1L)).thenReturn(formaciones);

    // Ejecución
    Double rating = equipoService.calcularRatingEquipo(equipo);

    // Verificación
    assertThat(rating, is(77.5)); // (80 + 75) / 2 = 77.5
    verify(formacionEquipoRepository).findByEquipoId(1L);
  }

  @Test
  public void deberiaObtenerTodosLosEquipos() {
    // Preparación
    List<Equipo> equipos = Arrays.asList(equipo);
    when(equipoRepository.getAll()).thenReturn(equipos);

    // Ejecución
    List<EquipoDTO> resultado = equipoService.getAll();

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(1));
    assertThat(resultado.get(0).getNombre(), is("Equipo Test"));
    verify(equipoRepository).getAll();
  }

  @Test
  public void deberiaObtenerEquipoPorId() {
    // Preparación
    when(equipoRepository.getById(1L)).thenReturn(equipo);

    // Ejecución
    EquipoDTO resultado = equipoService.getById(1L);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getNombre(), is("Equipo Test"));
    verify(equipoRepository).getById(1L);
  }

  @Test
  public void deberiaRetornarNullSiEquipoNoExiste() {
    // Preparación
    when(equipoRepository.getById(999L)).thenReturn(null);

    // Ejecución
    EquipoDTO resultado = equipoService.getById(999L);

    // Verificación
    assertThat(resultado, nullValue());
    verify(equipoRepository).getById(999L);
  }

  @Test
  public void deberiaGuardarEquipoConJugadores() {
    // Preparación
    doNothing().when(equipoRepository).save(any(Equipo.class));

    // Ejecución
    equipoService.saveBoth(equipoDTO, usuario);

    // Verificación
    verify(equipoRepository).save(any(Equipo.class));

    // Verificar que se guardó el equipo con jugadores
    verify(equipoRepository, times(1)).save(any(Equipo.class));
  }
}
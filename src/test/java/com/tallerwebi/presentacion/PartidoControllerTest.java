package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.service.PartidoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.controller.PartidoCotroller;
import com.tallerwebi.presentacion.dto.PartidoHistorialDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PartidoControllerTest {

  @Mock
  private PartidoService partidoService;

  @Mock
  private UsuarioService usuarioService;

  @InjectMocks
  private PartidoCotroller partidoController;

  @Mock
  private Model model;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpSession session;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    when(request.getSession()).thenReturn(session);
  }

  @Test
  public void deberiaRedirigirALoginSiUsuarioNoEstaLogueado() {
    // Preparación
    when(session.getAttribute("USUARIO_ID")).thenReturn(null);

    // Ejecución
    String vista = partidoController.historialPartidosJugados(model, request);

    // Verificación
    assertThat(vista, is("redirect:/login"));
    verify(session).getAttribute("USUARIO_ID");
    verifyNoInteractions(usuarioService);
    verifyNoInteractions(partidoService);
    verifyNoInteractions(model);
  }

  @Test
  public void deberiaRedirigirALoginSiUsuarioNoExiste() {
    // Preparación
    Long usuarioId = 1L;
    when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
    when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(null);

    // Ejecución
    String vista = partidoController.historialPartidosJugados(model, request);

    // Verificación
    assertThat(vista, is("redirect:/login"));
    verify(session).getAttribute("USUARIO_ID");
    verify(usuarioService).buscarUsuarioPorId(usuarioId);
    verifyNoInteractions(partidoService);
    verifyNoInteractions(model);
  }

  @Test
  public void deberiaMostrarHistorialConPartidosSiExisten() {
    // Preparación
    Long usuarioId = 1L;
    Long equipoId = 10L;

    Usuario usuario = new Usuario();
    Equipo equipo = new Equipo();
    equipo.setId(equipoId);
    equipo.setNombre("Equipo Test");
    usuario.setEquipo(equipo);

    List<PartidoHistorialDTO> historialPartidos = new ArrayList<>();
    PartidoHistorialDTO partido1 = new PartidoHistorialDTO();
    partido1.setId(1L);
    partido1.setGolesLocal(2);
    partido1.setGolesVisitante(1);
    partido1.setResultadoUsuario("GANASTE");
    historialPartidos.add(partido1);

    PartidoHistorialDTO partido2 = new PartidoHistorialDTO();
    partido2.setId(2L);
    partido2.setGolesLocal(0);
    partido2.setGolesVisitante(3);
    partido2.setResultadoUsuario("PERDISTE");
    historialPartidos.add(partido2);

    when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
    when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
    when(partidoService.obtenerPartidosJugadosPorEquipoId(equipoId)).thenReturn(historialPartidos);

    // Ejecución
    String vista = partidoController.historialPartidosJugados(model, request);

    // Verificación
    assertThat(vista, is("vista-historial-partidos"));
    verify(session).getAttribute("USUARIO_ID");
    verify(usuarioService).buscarUsuarioPorId(usuarioId);
    verify(partidoService).obtenerPartidosJugadosPorEquipoId(equipoId);
    verify(model).addAttribute("historialPartidosJugados", historialPartidos);
    verify(model, never()).addAttribute(eq("mensaje"), anyString());
  }

  @Test
  public void deberiaMostrarMensajeSiNoHayPartidosJugados() {
    // Preparación
    Long usuarioId = 1L;
    Long equipoId = 10L;

    Usuario usuario = new Usuario();
    Equipo equipo = new Equipo();
    equipo.setId(equipoId);
    equipo.setNombre("Equipo Test");
    usuario.setEquipo(equipo);

    List<PartidoHistorialDTO> historialVacio = new ArrayList<>();

    when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
    when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
    when(partidoService.obtenerPartidosJugadosPorEquipoId(equipoId)).thenReturn(historialVacio);

    // Ejecución
    String vista = partidoController.historialPartidosJugados(model, request);

    // Verificación
    assertThat(vista, is("vista-historial-partidos"));
    verify(session).getAttribute("USUARIO_ID");
    verify(usuarioService).buscarUsuarioPorId(usuarioId);
    verify(partidoService).obtenerPartidosJugadosPorEquipoId(equipoId);
    verify(model).addAttribute("mensaje", "Todavía no jugaste ningún partido.");
    verify(model, never()).addAttribute(eq("historialPartidosJugados"), any());
  }
//
//  @Test
//  public void deberiaManejarUsuarioSinEquipo() {
//    // Preparación
//    Long usuarioId = 1L;
//
//    Usuario usuario = new Usuario();
//    usuario.setEquipo(null); // Usuario sin equipo
//
//    when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
//    when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
//
//    // Ejecución
//    String vista = partidoController.historialPartidosJugados(model, request);
//
//    // Verificación
//    assertThat(vista, is("vista-historial-partidos"));
//    verify(session).getAttribute("USUARIO_ID");
//    verify(usuarioService).buscarUsuarioPorId(usuarioId);
//    verify(partidoService).obtenerPartidosJugadosPorEquipoId(null);
//    verify(model).addAttribute("mensaje", "Todavía no jugaste ningún partido.");
//  }
//
//  @Test
//  public void deberiaManejarExcepcionEnServicioDePartidos() {
//    // Preparación
//    Long usuarioId = 1L;
//    Long equipoId = 10L;
//
//    Usuario usuario = new Usuario();
//    Equipo equipo = new Equipo();
//    equipo.setId(equipoId);
//    equipo.setNombre("Equipo Test");
//    usuario.setEquipo(equipo);
//
//    when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
//    when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
//    when(partidoService.obtenerPartidosJugadosPorEquipoId(equipoId))
//        .thenThrow(new RuntimeException("Error en servicio"));
//
//    // Ejecución
//    String vista = partidoController.historialPartidosJugados(model, request);
//
//    // Verificación
//    assertThat(vista, is("vista-historial-partidos"));
//    verify(session).getAttribute("USUARIO_ID");
//    verify(usuarioService).buscarUsuarioPorId(usuarioId);
//    verify(partidoService).obtenerPartidosJugadosPorEquipoId(equipoId);
//    verify(model).addAttribute("mensaje", "Todavía no jugaste ningún partido.");
 // }
}
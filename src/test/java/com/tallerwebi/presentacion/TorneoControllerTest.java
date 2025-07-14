package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.controller.TorneoController;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.entities.Equipo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TorneoControllerTest {

   @Mock
   private TorneoService torneoService;

   @Mock
   private EquipoTorneoService equipoTorneoService;

   @Mock
   private UsuarioService usuarioService;

   @Mock
   private com.tallerwebi.dominio.service.SimularTorneoService simularTorneoService;

   @Mock
   private com.tallerwebi.dominio.repository.TorneoRepository torneoRepository;

   @Mock
   private com.tallerwebi.dominio.repository.EquipoTorneoRepository equipoTorneoRepository;

   @Mock
   private com.tallerwebi.dominio.repository.PartidoRepository partidoRepository;

   @Mock
   private com.tallerwebi.dominio.repository.FechaRepository fechaRepository;

   @Mock
   private com.tallerwebi.dominio.repository.NarracionRepository narracionRepository;

   @Mock
   private com.tallerwebi.dominio.service.FrasePartidoService frasePartidoService;

   @InjectMocks
   private TorneoController torneoController;

   @Mock
   private Model model;

   @Mock
   private HttpServletRequest request;
   @Mock
   private HttpSession session;
   @Mock
   private RedirectAttributes redirectAttributes;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
      when(request.getSession()).thenReturn(session);
   }

   @Test
   public void dadoUnIdTorneoValidoDebeMostrarDetalleTorneo() {
      Long idTorneo = 1L;
      Long usuarioId = 2L;
      TorneoDTO torneo = new TorneoDTO();
      torneo.setId(idTorneo);

      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);

      when(torneoService.getById(idTorneo)).thenReturn(torneo);

      // Mockear un Torneo con fechas vacías
      com.tallerwebi.dominio.model.entities.Torneo torneoEntity = mock(
            com.tallerwebi.dominio.model.entities.Torneo.class);
      when(torneoRepository.obtenerTorneoConFechas(idTorneo)).thenReturn(torneoEntity);
      when(torneoEntity.getFechas()).thenReturn(new HashSet<>());

      when(equipoTorneoRepository.getAllByTorneoId(idTorneo)).thenReturn(new ArrayList<>());

      String vistaDetalleTorneo = torneoController.detalleTorneo(idTorneo, model, request);

      verify(torneoService).getById(idTorneo);
      verify(model).addAttribute("torneo", torneo);
      verify(model).addAttribute("usuarioId", usuarioId);

      assertThat(vistaDetalleTorneo, is("detalle-torneo"));
   }

   @Test
   public void dadoUnIdTorneoValidoDebeMostrarDetalleTorneoConListaDeEquipos() {
      Long torneoId = 1L;
      Long usuarioId = 2L;
      TorneoDTO torneo = new TorneoDTO();
      torneo.setId(torneoId);

      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);

      when(torneoService.getById(torneoId)).thenReturn(torneo);

      // Mockear un Torneo con fechas vacías
      com.tallerwebi.dominio.model.entities.Torneo torneoEntity = mock(
            com.tallerwebi.dominio.model.entities.Torneo.class);
      when(torneoRepository.obtenerTorneoConFechas(torneoId)).thenReturn(torneoEntity);
      when(torneoEntity.getFechas()).thenReturn(new HashSet<>());

      when(equipoTorneoRepository.getAllByTorneoId(torneoId)).thenReturn(new ArrayList<>());

      String vistaDetalleTorneo = torneoController.detalleTorneo(torneoId, model, request);

      assertThat(vistaDetalleTorneo, is("detalle-torneo"));

      verify(model).addAttribute("torneo", torneo);
      verify(model).addAttribute("usuarioId", usuarioId);
      verify(torneoService).getById(torneoId);
   }

   @Test
   public void deberiaRetornarVistaDetalleTorneoInclusoSiElTorneoNoExiste() {
      Long usuarioId = 2L;
      Long idInexistente = 99L;

      when(request.getSession()).thenReturn(session);
      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);

      when(torneoService.getById(idInexistente)).thenReturn(null);

      // Mockear un Torneo con fechas vacías
      com.tallerwebi.dominio.model.entities.Torneo torneoEntity = mock(
            com.tallerwebi.dominio.model.entities.Torneo.class);
      when(torneoRepository.obtenerTorneoConFechas(idInexistente)).thenReturn(torneoEntity);
      when(torneoEntity.getFechas()).thenReturn(new HashSet<>());

      when(equipoTorneoRepository.getAllByTorneoId(idInexistente)).thenReturn(new ArrayList<>());

      String vistaDetalle = torneoController.detalleTorneo(idInexistente, model, request);

      verify(torneoService).getById(idInexistente);
      verify(model).addAttribute("usuarioId", usuarioId);

      assertThat(vistaDetalle, is("detalle-torneo"));
   }

   @Test
   public void unirseATorneoCuandoUsuarioNoEstaAutenticadoDebeRedirigirAError() {
      // Preparación
      Long torneoId = 1L;
      // La sesión NO devuelve USUARIO_ID
      when(session.getAttribute("USUARIO_ID")).thenReturn(null);

      // Ejecución
      String result = torneoController.unirseATorneo(torneoId, redirectAttributes, request);

      // Verificación
      assertThat(result, is("redirect:/torneo/detalle-torneo/" + torneoId));
      verify(redirectAttributes).addFlashAttribute("errorUnirse", "No estás autenticado. Por favor, inicia sesión.");
      // Asegurar que no se interactúa con los servicios después de la validación de
      // autenticación
      verifyNoInteractions(usuarioService);
      verifyNoInteractions(equipoTorneoService);
   }

   @Test
   public void unirseATorneoCuandoUsuarioNoExisteDebeRedirigirAError() {
      // Preparación
      Long torneoId = 1L;
      Long usuarioId = 10L;
      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
      when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(null);

      // Ejecución
      String result = torneoController.unirseATorneo(torneoId, redirectAttributes, request);

      // Verificación
      assertThat(result, is("redirect:/torneo/detalle-torneo/" + torneoId));
      verify(redirectAttributes).addFlashAttribute("errorUnirse", "Usuario no encontrado.");
      verify(usuarioService).buscarUsuarioPorId(usuarioId);
      verifyNoInteractions(equipoTorneoService);
   }

   @Test
   public void unirseATorneoCuandoUsuarioNoTieneEquipoDebeRedirigirANuevoEquipoConError() {
      // Preparación
      Long torneoId = 1L;
      Long usuarioId = 10L;
      Usuario usuario = new Usuario();
      usuario.setId(usuarioId);
      usuario.setEquipo(null);

      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
      when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);

      // Ejecución
      String result = torneoController.unirseATorneo(torneoId, redirectAttributes, request);

      // Verificación
      assertThat(result, is("redirect:/torneo/nuevo-equipo"));
      verify(redirectAttributes).addFlashAttribute("errorUnirse",
            "No tienes un equipo asignado. Crea un equipo primero.");
      verify(usuarioService).buscarUsuarioPorId(usuarioId);
      verifyNoInteractions(equipoTorneoService);
   }

   @Test
   public void unirseATorneoExitosoDebeUnirEquipoYRedirigirADetalleTorneoConMensaje() {
      // Preparación
      Long torneoId = 1L;
      Long usuarioId = 10L;
      Long equipoId = 100L;

      Usuario usuario = new Usuario();
      usuario.setId(usuarioId);
      Equipo equipo = new Equipo();
      equipo.setId(equipoId);
      usuario.setEquipo(equipo);

      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
      when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
      doNothing().when(equipoTorneoService).unirseTorneo(torneoId, equipoId);

      // Ejecución
      String result = torneoController.unirseATorneo(torneoId, redirectAttributes, request);

      // Verificación
      assertThat(result, is("redirect:/torneo/detalle-torneo/" + torneoId));
      verify(usuarioService).buscarUsuarioPorId(usuarioId);
      verify(equipoTorneoService).unirseTorneo(torneoId, equipoId);
      verify(redirectAttributes).addFlashAttribute("mensajeTorneo", "¡Te uniste al torneo con éxito!");
      verify(redirectAttributes, never()).addFlashAttribute(eq("errorUnirse"), anyString());
   }

   @Test
   public void unirseATorneoFallidoPorExcepcionEnServicioDebeRedirigirADetalleTorneoConError() {
      // Preparación
      Long torneoId = 1L;
      Long usuarioId = 10L;
      Long equipoId = 100L;
      String errorMessage = "Error simulado al unirse";

      Usuario usuario = new Usuario();
      usuario.setId(usuarioId);
      Equipo equipo = new Equipo();
      equipo.setId(equipoId);
      usuario.setEquipo(equipo);

      when(session.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
      when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);
      doThrow(new IllegalArgumentException(errorMessage)).when(equipoTorneoService).unirseTorneo(torneoId, equipoId);

      // Ejecución
      String result = torneoController.unirseATorneo(torneoId, redirectAttributes, request);

      // Verificación
      assertThat(result, is("redirect:/torneo/detalle-torneo/" + torneoId));
      verify(usuarioService).buscarUsuarioPorId(usuarioId);
      verify(equipoTorneoService).unirseTorneo(torneoId, equipoId);
      verify(redirectAttributes).addFlashAttribute("errorUnirse", errorMessage);
      verify(redirectAttributes, never()).addFlashAttribute(eq("mensajeTorneo"), anyString());
   }
}
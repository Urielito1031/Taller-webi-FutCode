package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.presentacion.controller.HomeController;
import com.tallerwebi.presentacion.dto.TorneoDTO;
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

public class HomeControllerTest {

   @Mock
   private TorneoService torneoService;

   @Mock
   private UsuarioService usuarioService;

   @Mock
   private EquipoTorneoService equipoTorneoService;

   @InjectMocks
   private HomeController homeController;

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
   public void deberiaMostrarLaVistaConTorneosSiLaListaNoEsVaciaEnHomeSiUsuarioEstaLogueado() {
      // Preparación
      List<TorneoDTO> torneos = new ArrayList<>();
      torneos.add(new TorneoDTO());

      Usuario usuario = new Usuario();
      Equipo equipo = new Equipo();
      equipo.setNombre("Equipo Test");
      usuario.setEquipo(equipo);

      when(session.getAttribute("USUARIO_ID")).thenReturn(1L);
      when(torneoService.getAll()).thenReturn(torneos);
      when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);
      when(equipoTorneoService.getAllByTorneoId(any())).thenReturn(new ArrayList<>());

      // Ejecución
      String vistaHome = homeController.irAHome(model, request);

      // Verificación
      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", torneos);
      verify(model).addAttribute("usuario", usuario);
      verify(model).addAttribute("equipoNombre", "Equipo Test");
      verify(model).addAttribute("torneosUnidos", new ArrayList<>());
      verify(model, never()).addAttribute(eq("mensajeTorneo"), anyString());
      verify(session).getAttribute("USUARIO_ID");
      verify(usuarioService).buscarUsuarioPorId(1L);
   }

   @Test
   public void deberiaMostrarLaVistaConMensajeTorneoSiLaListaEstaVaciaEnHomeSiUsuarioEstaLogueado() {
      // Preparación
      Usuario usuario = new Usuario();
      Equipo equipo = new Equipo();
      equipo.setNombre("Equipo Test");
      usuario.setEquipo(equipo);

      when(session.getAttribute("USUARIO_ID")).thenReturn(1L);
      when(torneoService.getAll()).thenReturn(new ArrayList<>());
      when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);
      when(equipoTorneoService.getAllByTorneoId(any())).thenReturn(new ArrayList<>());

      // Ejecución
      String vistaHome = homeController.irAHome(model, request);

      // Verificación
      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", new ArrayList<>());
      verify(model).addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      verify(model).addAttribute("usuario", usuario);
      verify(model).addAttribute("equipoNombre", "Equipo Test");
      verify(model).addAttribute("torneosUnidos", new ArrayList<>());
      verify(session).getAttribute("USUARIO_ID");
      verify(usuarioService).buscarUsuarioPorId(1L);
   }

   @Test
   public void deberiaRedirigirALoginSiUsuarioNoEstaLogueadoCuandoNavegaAHome() {
      // Preparación
      when(session.getAttribute("USUARIO_ID")).thenReturn(null);

      // Ejecución
      String redirectPath = homeController.irAHome(model, request);

      // Verificación
      assertThat(redirectPath, is("redirect:/login"));
      verify(session).getAttribute("USUARIO_ID");
      verifyNoInteractions(torneoService);
      verifyNoInteractions(usuarioService);
      verifyNoInteractions(equipoTorneoService);
      verifyNoInteractions(model);
   }

   @Test
   public void deberiaMostrarSinEquipoSiUsuarioNoTieneEquipo() {
      // Preparación
      Usuario usuario = new Usuario();
      usuario.setEquipo(null); // Usuario sin equipo

      when(session.getAttribute("USUARIO_ID")).thenReturn(1L);
      when(torneoService.getAll()).thenReturn(new ArrayList<>());
      when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);

      // Ejecución
      String vistaHome = homeController.irAHome(model, request);

      // Verificación
      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", new ArrayList<>());
      verify(model).addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      verify(model).addAttribute("usuario", usuario);
      verify(model).addAttribute("equipoNombre", "Sin equipo");
      verify(model).addAttribute("torneosUnidos", new ArrayList<>());
      verify(session).getAttribute("USUARIO_ID");
      verify(usuarioService).buscarUsuarioPorId(1L);
   }

}
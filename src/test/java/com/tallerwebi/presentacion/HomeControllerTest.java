package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.service.TorneoService;
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

      when(session.getAttribute("USUARIO_ID")).thenReturn(1L);

      when(torneoService.getAll()).thenReturn(torneos);

      // Ejecución
      String vistaHome = homeController.irAHome(model, request);

      // Verificación
      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", torneos);
      verify(model, never()).addAttribute(eq("mensajeTorneo"), anyString());
      verify(session).getAttribute("USUARIO_ID");
   }

   @Test
   public void deberiaMostrarLaVistaConMensajeTorneoSiLaListaEstaVaciaEnHomeSiUsuarioEstaLogueado() {
      // Preparación
      when(session.getAttribute("USUARIO_ID")).thenReturn(1L);
      when(torneoService.getAll()).thenReturn(new ArrayList<>());

      // Ejecución
      String vistaHome = homeController.irAHome(model, request);

      // Verificación
      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", new ArrayList<>());
      verify(model).addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      verify(session).getAttribute("USUARIO_ID");
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
      verifyNoInteractions(model);
   }



}
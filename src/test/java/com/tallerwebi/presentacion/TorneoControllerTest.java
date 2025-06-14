package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.controller.TorneoController;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TorneoControllerTest {

   @Mock
   private TorneoService torneoService;

   @InjectMocks
   private TorneoController torneoController;

   @Mock
   private Model model;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @Test
   public void deberiaMostrarLaVistaConTorneosSiLaListaNoEsVacia() {
      List<TorneoDTO> torneos = new ArrayList<>();
      torneos.add(new TorneoDTO());
      when(torneoService.getAll()).thenReturn(torneos);

      String vistaHome = torneoController.irAHome(model);

      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", torneos);
      verify(model, never()).addAttribute(eq("mensajeTorneo"), anyString());
   }

   @Test
   public void deberiaMostrarLaVistaConMensajeTorneoSiLaListaEstaVacia() {
      when(torneoService.getAll()).thenReturn(new ArrayList<>());

      String vistaHome = torneoController.irAHome(model);

      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", is(empty()));
      verify(model).addAttribute("mensajeTorneo", is("No hay torneos para mostrar"));
   }

   @Test
   public void deberiaMostrarLaVistaConMensajeTorneoSiLaListaEsNull() {
      when(torneoService.getAll()).thenReturn(null);

      String vistaHome = torneoController.irAHome(model);

      assertThat(vistaHome, is("home"));
      verify(model).addAttribute("torneos", is(nullValue()));
      verify(model).addAttribute("mensajeTorneo", is("No hay torneos para mostrar"));
   }
}
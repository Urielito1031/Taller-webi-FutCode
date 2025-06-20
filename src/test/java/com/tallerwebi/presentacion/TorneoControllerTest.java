package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.controller.TorneoController;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class TorneoControllerTest {

   @Mock
   private TorneoService torneoService;

   @Mock
   private EquipoTorneoService equipoTorneoService;

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
      verify(model).addAttribute("torneos", new ArrayList<>());

      verify(model).addAttribute("mensajeTorneo", "No hay torneos para mostrar");
   }

   @Test
   public void deberiaMostrarDetalleTorneoCuandoSeVerificaUnIdValido(){
      Long idTorneo = 1L;

      TorneoDTO torneo = new TorneoDTO();
      torneo.setId(idTorneo);

      when(torneoService.getById(idTorneo)).thenReturn(torneo);


      String vistaDetalleTorneo = torneoController.detalleTorneo(idTorneo, model);
      verify(torneoService).getById(idTorneo);

      verify(model).addAttribute("torneo", torneo);

      assertThat(vistaDetalleTorneo,is("detalle-torneo"));
   }
   @Test
   public void deberiaMostrarDetalleTorneoCuandoSeVerificaUnIdValidoIncluyendoListaEquipos() {
      Long torneoId = 1L;
      TorneoDTO torneo = new TorneoDTO( );
      List<EquipoTorneoDTO> equipoTorneoList = new ArrayList<>();


      when(torneoService.getById(torneoId)).thenReturn(torneo);
      when(equipoTorneoService.getAllByTorneoId(torneoId)).thenReturn(equipoTorneoList);

      String vistaDetalleTorneo = torneoController.detalleTorneo(torneoId, model);

      assertThat(vistaDetalleTorneo, is("detalle-torneo"));

      verify(model).addAttribute("torneo", torneo);
      verify(model).addAttribute("torneoEquipos", equipoTorneoList);

      verify(torneoService).getById(torneoId);
      verify(equipoTorneoService).getAllByTorneoId(torneoId);


   }
   @Test
   public void deberiaRetornarNullSiElTorneoNoExisteAlVerDetalle() {
      Long idInexistente = 99L;
      when(torneoService.getById(idInexistente)).thenReturn(null);

      String vistaDetalle = torneoController.detalleTorneo(idInexistente, model);

      verify(torneoService).getById(idInexistente);

      verify(model, never()).addAttribute(eq("torneo"), any(TorneoDTO.class));
     assertThat(vistaDetalle, is("detalle-torneo"));

   }

}
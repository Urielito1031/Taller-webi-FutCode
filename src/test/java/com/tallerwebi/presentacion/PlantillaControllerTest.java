package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.presentacion.controller.PlantillaController;
import com.tallerwebi.presentacion.dto.EsquemaDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlantillaControllerTest {

   @Mock
   private PlantillaService plantillaService;

   @InjectMocks
   private PlantillaController plantillaController;

   @Mock
   private Model model;



   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @Test
   public void DadoQueMostramosLaVistaDePlantillaDebeEstablecerAtributos() {
      EsquemaDTO formacion = crearEsquemaDTOValido();
      when(plantillaService.initPlantillaBase()).thenReturn(formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());

      String view = plantillaController.showViewPlantilla(model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute(eq("formacion"), eq(formacion));
      verify(model).addAttribute(eq("esquemas"), eq(esquemas));
   }

   @Test
   public void DadoQueCambiamosLaFormacionDebeDevolverVistaPlantilla() {
      String esquemaTexto = "5-3-2";
      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      EsquemaDTO formacion = crearEsquemaDTOValido();
      formacion.setEsquema(esquemaSeleccionado);
      when(plantillaService.initPlantillaBase()).thenReturn(formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());

      String view = plantillaController.cambiarFormacion(esquemaTexto, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute(eq("formacion"), eq(formacion));
      verify(model).addAttribute(eq("esquemas"), eq(esquemas));
   }

   @Test
   public void DadoQueGuardamosLaFormacionConExitoDebeMostrarMensajeYAtributos() {
      EsquemaDTO formacion = crearEsquemaDTOValido();
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      EsquemaDTO baseFormacion = crearEsquemaDTOValido();
      baseFormacion.setId(1L);
      baseFormacion.setEsquema(FormacionEsquema.CINCO_TRES_DOS);
      when(plantillaService.save(formacion)).thenReturn(true);
      when(plantillaService.initPlantillaBase()).thenReturn(baseFormacion);

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      InOrder inOrder = inOrder(model);
      inOrder.verify(model).addAttribute(eq("message"), eq("Formación guardada con éxito!"));
      inOrder.verify(model).addAttribute(eq("formacion"), eq(baseFormacion));
      inOrder.verify(model).addAttribute(eq("esquemas"), eq(Arrays.asList(FormacionEsquema.values())));
   }



   private EsquemaDTO crearEsquemaDTOValido() {
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setId(1L);
      formacion.setEquipoId(1L);
     // formacion.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      List<PosicionJugadorDTO> alineacion = new ArrayList<>();
      for (int i = 0; i < 11; i++) {
         PosicionJugadorDTO posicion = new PosicionJugadorDTO();
         posicion.setJugadorId((long) (i + 1));
         posicion.setPosicionEnCampo(PosicionEnum.values()[i % PosicionEnum.values().length]);
         alineacion.add(posicion);
      }
      formacion.setAlineacion(alineacion);
      return formacion;
   }
}
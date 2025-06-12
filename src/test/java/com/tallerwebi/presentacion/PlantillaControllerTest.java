package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.presentacion.controller.PlantillaController;
import com.tallerwebi.presentacion.dto.EsquemaDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PlantillaControllerTest {

   @Mock
   private PlantillaService plantillaService;

   @InjectMocks
   private PlantillaController plantillaController;

   @Mock
   private Model model;

   @Mock
   private HttpServletRequest request;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
      RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
   }

   @Test
   public void shouldReturnVistaPlantillaViewAndSetAttributes() {
      EsquemaDTO formacion = new EsquemaDTO();
      when(plantillaService.initPlantillaBase()).thenReturn(formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());

      String view = plantillaController.showViewPlantilla(model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", formacion);
      verify(model).addAttribute("esquemas", esquemas);
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldChangeFormationAndReturnVistaPlantillaView() {
      String esquemaTexto = "4-4-2";
      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setEsquema(esquemaSeleccionado);
      when(plantillaService.initPlantillaBase()).thenReturn(formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());

      String view = plantillaController.cambiarFormacion(esquemaTexto, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", formacion);
      verify(model).addAttribute("esquemas", esquemas);
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldSaveFormationSuccessfully() {
      EsquemaDTO formacion = createValidEsquemaDTO();
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      when(plantillaService.save(formacion)).thenReturn(true);
      when(plantillaService.initPlantillaBase()).thenReturn(new EsquemaDTO());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("message", "Formación guardada con éxito!");
      verify(model).addAttribute("formacion", new EsquemaDTO());
      verify(model).addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldReturnErrorWhenFormacionIsNull() {
      EsquemaDTO formacion = null;
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      when(plantillaService.initPlantillaBase()).thenReturn(new EsquemaDTO());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", new EsquemaDTO());
      verify(model).addAttribute("error", "Formación no encontrada.");
      verify(model).addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldReturnErrorWhenEsquemaIsNull() {
      EsquemaDTO formacion = new EsquemaDTO();
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      result.rejectValue("esquema", "error.esquema", "El esquema es obligatorio");
      when(plantillaService.initPlantillaBase()).thenReturn(new EsquemaDTO());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", new EsquemaDTO());
      verify(model).addAttribute("error", "El esquema es obligatorio");
      verify(model).addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldReturnErrorWhenAlineacionIsInvalid() {
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setEquipoId(1L);
      formacion.setAlineacion(new ArrayList<>()); // Lista vacía (menos de 11)
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      result.rejectValue("alineacion", "size.alineacion", "Debes tener exactamente 11 jugadores");
      when(plantillaService.initPlantillaBase()).thenReturn(new EsquemaDTO());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", new EsquemaDTO());
      verify(model).addAttribute("error", "Debes tener exactamente 11 jugadores");
      verify(model).addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldReturnErrorWhenEquipoIdIsInvalid() {
      EsquemaDTO formacion = createValidEsquemaDTO();
      formacion.setEquipoId(0L); // Equipo ID inválido
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      when(plantillaService.initPlantillaBase()).thenReturn(new EsquemaDTO());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", new EsquemaDTO());
      verify(model).addAttribute("error", "El ID del equipo no está definido. Contacta al administrador.");
      verify(model).addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      verifyNoMoreInteractions(model);
   }

   @Test
   public void shouldReturnErrorWhenSaveFails() {
      EsquemaDTO formacion = createValidEsquemaDTO();
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      when(plantillaService.save(formacion)).thenReturn(false);
      when(plantillaService.initPlantillaBase()).thenReturn(new EsquemaDTO());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", new EsquemaDTO());
      verify(model).addAttribute("error", "Error al guardar la formación.");
      verify(model).addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      verifyNoMoreInteractions(model);
   }

   private EsquemaDTO createValidEsquemaDTO() {
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setEquipoId(1L);
      formacion.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
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
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.service.PlantillaServiceImpl;
import com.tallerwebi.presentacion.controller.PlantillaController;
import com.tallerwebi.presentacion.dto.FormacionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ControladorPlantillaTest {

   @Mock
   private PlantillaServiceImpl service;

   @InjectMocks
   private PlantillaController controller;

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
   public void testShowViewPlantilla() {
      // Arrange
      FormacionDTO formacion = new FormacionDTO();
      when(service.initPlantillaBase()).thenReturn(formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());

      // Act
      String view = controller.showViewPlantilla(model);

      // Assert
      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", formacion);
      verify(model).addAttribute("esquemas", esquemas);
      verifyNoMoreInteractions(model);
   }

   @Test
   public void testCambiarFormacion() {
      // Arrange
      String esquemaTexto = "4-4-2";
      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      FormacionDTO formacion = new FormacionDTO();
      formacion.setEsquema(esquemaSeleccionado);
      when(service.initPlantillaBase()).thenReturn(formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());

      // Act
      String view = controller.cambiarFormacion(esquemaTexto, model);

      // Assert
      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute("formacion", formacion);
      verify(model).addAttribute("esquemas", esquemas);
      verifyNoMoreInteractions(model);
   }

}
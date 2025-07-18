package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.service.JugadorService;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.dominio.service.UsuarioService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlantillaControllerTest {

   @Mock
   private PlantillaService plantillaService;

   @Mock
   private JugadorService jugadorService;

   @Mock
   private UsuarioService usuarioService;

   @InjectMocks
   private PlantillaController plantillaController;

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
      when(session.getAttribute("USUARIO_ID")).thenReturn(1L);

      Usuario usuarioMock = new Usuario();
      Equipo equipoMock = new Equipo();
      equipoMock.setId(1L);
      usuarioMock.setEquipo(equipoMock);

      when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuarioMock);
   }

   @Test
   public void DadoQueMostramosLaVistaDePlantillaDebeEstablecerAtributos() {
      EsquemaDTO formacion = crearEsquemaDTOValido();

      when(plantillaService.getFormacionPorEquipoId(1L)).thenReturn(formacion);
      when(jugadorService.getAllByEquipoId(1L)).thenReturn(new ArrayList<>());

      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      String view = plantillaController.showViewPlantilla(model, request);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute(eq("jugadores"), any());
      verify(model).addAttribute(eq("formacion"), eq(formacion));
      verify(model).addAttribute(eq("esquemas"), eq(esquemas));
   }

   @Test
   public void DadoQueCambiamosLaFormacionDebeDevolverVistaPlantilla() {
      String esquemaTexto = "5-3-2";
      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      EsquemaDTO formacion = crearEsquemaDTOValido();
      formacion.setEsquema(esquemaSeleccionado);

      when(plantillaService.initPlantillaBase(1L)).thenReturn(formacion);
      when(jugadorService.getAllByEquipoId(1L)).thenReturn(new ArrayList<>());

      String view = plantillaController.cambiarFormacion(esquemaTexto, model, request);

      assertThat(view, is("vista-plantilla"));
      verify(model).addAttribute(eq("jugadores"), any());
      verify(model).addAttribute(eq("formacion"), eq(formacion));
      verify(model).addAttribute(eq("esquemas"), eq(Arrays.asList(FormacionEsquema.values())));
   }

   @Test
   public void DadoQueGuardamosLaFormacionConExitoDebeMostrarMensajeYAtributos() {
      EsquemaDTO formacion = crearEsquemaDTOValido();
      BindingResult result = new BeanPropertyBindingResult(formacion, "formacion");
      EsquemaDTO baseFormacion = crearEsquemaDTOValido();
      baseFormacion.setId(1L);
      baseFormacion.setEsquema(FormacionEsquema.CINCO_TRES_DOS);

      when(plantillaService.save(formacion)).thenReturn(true);
      when(plantillaService.getFormacionPorEquipoId(formacion.getEquipoId())).thenReturn(baseFormacion);
      when(jugadorService.getAllByEquipoId(formacion.getEquipoId())).thenReturn(new ArrayList<>());

      String view = plantillaController.guardarFormacion(formacion, result, model);

      assertThat(view, is("vista-plantilla"));
      InOrder inOrder = inOrder(model);
      inOrder.verify(model).addAttribute(eq("formacion"), eq(baseFormacion));
      inOrder.verify(model).addAttribute(eq("jugadores"), any());
      inOrder.verify(model).addAttribute(eq("esquemas"), eq(Arrays.asList(FormacionEsquema.values())));
      inOrder.verify(model).addAttribute(eq("message"), eq("Formación guardada con éxito!"));
   }

   private EsquemaDTO crearEsquemaDTOValido() {
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setId(1L);
      formacion.setEquipoId(1L);

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

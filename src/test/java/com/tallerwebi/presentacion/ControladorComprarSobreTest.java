package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.controller.ComprarSobreController;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ControladorComprarSobreTest {

    private SobreService sobreService;
    private UsuarioService usuarioService;
    private ComprarSobreController comprarSobreController;
    private HttpServletRequest request;
    private HttpSession session;

    private final Long USUARIO_ID = 1L;
    private Usuario usuarioMock;

    @BeforeEach
    public void setUp() {
        // Inicialización de mocks
        sobreService = Mockito.mock(SobreService.class);
        usuarioService = Mockito.mock(UsuarioService.class);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);

        // Inicialización del controlador con los mocks inyectados
        comprarSobreController = new ComprarSobreController(usuarioService, sobreService);

        // Configuración común para la sesión del usuario
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO_ID")).thenReturn(USUARIO_ID);

        // Configuración común para el usuario mock
        usuarioMock = new Usuario();
        usuarioMock.setId(USUARIO_ID);
        usuarioMock.setMonedas(100000.0); // Monedas iniciales para la mayoría de los tests
        when(usuarioService.buscarUsuarioPorId(USUARIO_ID)).thenReturn(usuarioMock);
    }

    // TESTS PARA mostrarVistaComprarSobres()

    @Test
    public void deberiaMostrarVistaComprarSobreConSobresDisponibles() {
        // GIVEN
        List<SobreDTO> sobresEsperados = new ArrayList<>();
        sobresEsperados.add(new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png"));
        sobresEsperados.add(new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png"));
        when(sobreService.obtenerSobresDTO()).thenReturn(sobresEsperados);
        when(usuarioService.obtenerSobresDelUsuario(USUARIO_ID)).thenReturn(new ArrayList<>()); // El usuario no tiene sobres
        // Configuración de monedas del usuario ya está en beforeEach

        // WHEN
        ModelAndView mav = comprarSobreController.mostrarVistaComprarSobres(request);

        // THEN
        assertThat(mav.getViewName(), is("vista-comprar-sobres"));
        assertThat((List<SobreDTO>) mav.getModel().get("sobres"), is(sobresEsperados));
        assertThat(mav.getModel().get("cantidadSobres"), is(0));
        assertThat(mav.getModel().get("monedas"), is(usuarioMock.getMonedas()));
        verify(sobreService, times(1)).obtenerSobresDTO();
        verify(usuarioService, times(1)).obtenerSobresDelUsuario(USUARIO_ID);
        verify(usuarioService, times(1)).buscarUsuarioPorId(USUARIO_ID);
    }

    @Test
    public void deberiaMostrarVistaComprarSobreSiNoHaySobresParaComprar() {
        // GIVEN
        when(sobreService.obtenerSobresDTO()).thenReturn(new ArrayList<>());
        when(usuarioService.obtenerSobresDelUsuario(USUARIO_ID)).thenReturn(new ArrayList<>()); // El usuario no tiene sobres

        // WHEN
        ModelAndView mav = comprarSobreController.mostrarVistaComprarSobres(request);

        // THEN
        assertThat(mav.getViewName(), is("vista-comprar-sobres"));
        assertThat((List<SobreDTO>) mav.getModel().get("sobres"), is(empty()));
        assertThat(mav.getModel().get("cantidadSobres"), is(0));
        assertThat(mav.getModel().get("monedas"), is(usuarioMock.getMonedas()));
        verify(sobreService, times(1)).obtenerSobresDTO();
        verify(usuarioService, times(1)).obtenerSobresDelUsuario(USUARIO_ID);
        verify(usuarioService, times(1)).buscarUsuarioPorId(USUARIO_ID);
    }

    @Test
    public void deberiaMostrarVistaComprarSobreConCantidadDeSobresCorrectaSiElUsuarioTieneSobres() {
        // GIVEN
        List<SobreDTO> sobresDisponibles = new ArrayList<>();
        sobresDisponibles.add(new SobreDTO("Bronce", 100.0, TipoSobre.BRONCE, ""));
        when(sobreService.obtenerSobresDTO()).thenReturn(sobresDisponibles);

        List<SobreDTO> sobresDelUsuario = new ArrayList<>();
        sobresDelUsuario.add(new SobreDTO("Bronce", 100.0, TipoSobre.BRONCE, ""));
        sobresDelUsuario.add(new SobreDTO("Plata", 200.0, TipoSobre.PLATA, ""));
        when(usuarioService.obtenerSobresDelUsuario(USUARIO_ID)).thenReturn(sobresDelUsuario);

        // WHEN
        ModelAndView mav = comprarSobreController.mostrarVistaComprarSobres(request);

        // THEN
        assertThat(mav.getViewName(), is("vista-comprar-sobres"));
        assertThat(mav.getModel().get("cantidadSobres"), is(2)); // Ahora el usuario tiene 2 sobres
        assertThat(mav.getModel().get("sobres"), is(sobresDisponibles));
        verify(usuarioService, times(1)).obtenerSobresDelUsuario(USUARIO_ID);
        verify(usuarioService, times(1)).buscarUsuarioPorId(USUARIO_ID);
    }




    // TESTS PARA crearSobre()

    @Test
    public void deberiaAgregarSobreYRedirigirAMisSobresSiCompraExitosa() throws MonedasInsuficientes, UsuarioNoEncontrado {
        // GIVEN
        SobreDTO sobreCreado = new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "img.png");
        when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobreCreado);
        // El usuarioMock ya tiene 100000 monedas, suficiente para un sobre de 2500.0
        when(usuarioService.agregarSobreAJugador(eq(USUARIO_ID), any(SobreDTO.class))).thenReturn(true);

        // WHEN
        ModelAndView mav = comprarSobreController.crearSobre(TipoSobre.BRONCE, request);

        // THEN
        assertThat(mav.getViewName(), is("redirect:/jugador/mis-sobres"));
        verify(sobreService, times(1)).crearSobre(TipoSobre.BRONCE);
        verify(usuarioService, times(1)).agregarSobreAJugador(eq(USUARIO_ID), eq(sobreCreado));
    }


    @Test
    public void deberiaManejarErrorSiTipoDeSobreDesconocidoAlCrearSobre() throws MonedasInsuficientes, UsuarioNoEncontrado {
        // GIVEN
        // Simular que crearSobre del servicio lanza una excepción TipoDeSobreDesconocido
        doThrow(new TipoDeSobreDesconocido())
          .when(sobreService).crearSobre(any(TipoSobre.class));

        // WHEN
        // Aquí esperamos que la excepción se propague, ya que el controller no la captura explícitamente.
        // O si la captura, debería redirigir a un error o mostrar un mensaje.
        // Basado en tu código, esta excepción NO ES CAPTURADA en el POST /agregarSobre.
        // Por lo tanto, el test debería verificar que la excepción es lanzada.

        // Para este escenario, necesitamos probar que la excepción se lanza,
        // lo que implica cambiar la forma de la aserción o el manejo del controlador.
        // Si el controlador no la maneja, el servidor de aplicaciones (Jetty) la capturaría
        // y mostraría una página de error 500.

        // Dado que el controlador NO MANEJA TipoDeSobreDesconocido en el @PostMapping,
        // el test correcto es esperar la excepción.
        org.junit.jupiter.api.Assertions.assertThrows(TipoDeSobreDesconocido.class, () -> {
            comprarSobreController.crearSobre(TipoSobre.BRONCE, request);
        });

        // THEN
        verify(sobreService, times(1)).crearSobre(TipoSobre.BRONCE);
        // No se deberían llamar otros métodos del servicio de usuario si falla la creación del sobre
        verify(usuarioService, never()).agregarSobreAJugador(anyLong(), any(SobreDTO.class));
        verify(usuarioService, never()).buscarUsuarioPorId(anyLong()); // No se busca el usuario si falla antes
    }


    // TESTS PARA mostrarVistaMisSobres()

    @Test
    public void deberiaMostrarVistaMisSobresConSobresDelUsuario() {
        // GIVEN
        List<SobreDTO> sobresDelUsuarioEsperados = new ArrayList<>();
        sobresDelUsuarioEsperados.add(new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "img.png"));
        sobresDelUsuarioEsperados.add(new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "img2.png"));
        when(usuarioService.obtenerSobresDelUsuario(USUARIO_ID)).thenReturn(sobresDelUsuarioEsperados);
        // usuarioMock ya tiene sus monedas configuradas en beforeEach

        // WHEN
        ModelAndView mav = comprarSobreController.mostrarVistaMisSobres(request);

        // THEN
        assertThat(mav.getViewName(), is("vista-mis-sobres"));
        assertThat((List<SobreDTO>) mav.getModel().get("sobres"), is(sobresDelUsuarioEsperados));
        assertThat(mav.getModel().get("cantidadSobres"), is(sobresDelUsuarioEsperados.size()));
        assertThat(mav.getModel().get("monedas"), is(usuarioMock.getMonedas()));
        verify(usuarioService, times(1)).obtenerSobresDelUsuario(USUARIO_ID);
        verify(usuarioService, times(1)).buscarUsuarioPorId(USUARIO_ID);
    }

    @Test
    public void deberiaMostrarVistaMisSobresSiElUsuarioNoTieneSobres() {
        // GIVEN
        when(usuarioService.obtenerSobresDelUsuario(USUARIO_ID)).thenReturn(new ArrayList<>());
        // usuarioMock ya tiene sus monedas configuradas en beforeEach

        // WHEN
        ModelAndView mav = comprarSobreController.mostrarVistaMisSobres(request);

        // THEN
        assertThat(mav.getViewName(), is("vista-mis-sobres"));
        assertThat((List<SobreDTO>) mav.getModel().get("sobres"), is(empty()));
        assertThat(mav.getModel().get("cantidadSobres"), is(0));
        assertThat(mav.getModel().get("monedas"), is(usuarioMock.getMonedas()));
        verify(usuarioService, times(1)).obtenerSobresDelUsuario(USUARIO_ID);
        verify(usuarioService, times(1)).buscarUsuarioPorId(USUARIO_ID);
    }



    @Test
    public void deberiaAgregarSobreYRedirigirAMisSobresInclusoSiAgregarDevuelveFalse() throws MonedasInsuficientes, UsuarioNoEncontrado {
        // GIVEN
        SobreDTO sobreCreado = new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "img.png");
        when(sobreService.crearSobre(TipoSobre.PLATA)).thenReturn(sobreCreado);
        when(usuarioService.agregarSobreAJugador(eq(USUARIO_ID), any(SobreDTO.class))).thenReturn(false);

        // WHEN
        ModelAndView mav = comprarSobreController.crearSobre(TipoSobre.PLATA, request);

        // THEN
        assertThat(mav.getViewName(), is("redirect:/jugador/mis-sobres"));
        verify(sobreService, times(1)).crearSobre(TipoSobre.PLATA);
        verify(usuarioService, times(1)).agregarSobreAJugador(eq(USUARIO_ID), eq(sobreCreado));
    }

}
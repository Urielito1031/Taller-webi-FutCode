package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.SobreServiceImpl;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class ControladorComprarSobreTest {

    private SobreService sobreService;
    private UsuarioService usuarioService;
    private ComprarSobreController comprarSobreController;


    @BeforeEach
    public void setUp() {
        sobreService = Mockito.mock(SobreService.class);
        usuarioService = Mockito.mock(UsuarioService.class);
        comprarSobreController = new ComprarSobreController(usuarioService, sobreService);
    }

//   @Test
//    public void testShowViewComprarSobre(){
//        List<SobreDTO> sobresEsperados = new ArrayList<>();
//        sobresEsperados.add(new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png"));
//        sobresEsperados.add(new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png"));
//        sobresEsperados.add(new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "sobreFutCodeOro.png"));
//        sobresEsperados.add(new SobreDTO("Sobre Especial", 10000.0, TipoSobre.ESPECIAL, "sobreFutCodeEspecial.png"));
//
//        when(sobreService.obtenerSobresDTO()).thenReturn(sobresEsperados);
//
//        // Pide HttpServletRequest request
//        ModelAndView mav = comprarSobreController.mostrarVistaComprarSobres();
//        String vista = mav.getViewName();
//
//        assertThat(vista, is("vista-comprar-sobres"));
//        assertThat(mav.getModel().get("sobres"), is(sobresEsperados));
//    }


//    public void dadoQueCreoUnSobreMeDevuelveUnMensajeDeExito(){
//        SobreDTO sobre = new SobreDTO(TipoSobre.BRONCE);
//        Mockito.when(this.sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);
//
//        HttpSession session =
//        String respuesta = this.comprarSobreController.crearSobre(TipoSobre.BRONCE, mockitoSession().getId);
//
//        assertThat(respuesta, is("Sobre creado con exito"));
//    }
//
//    @Test
//    public void dadoQueElSobreNoSePuedeCrearDevuelveUnMensajeDeError(){
//        Mockito.when(this.sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(null);
//
//        HttpSession request = Mockito.mock(HttpSession.class);
//
//        String respuesta = this.comprarSobreController.crearSobre(TipoSobre.BRONCE, request);
//
//        assertThat(respuesta, is("No se pudo crear el sobre"));
//    }




}

package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.presentacion.controller.ComprarSobreController;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

public class ControladorComprarSobreTest {

    private SobreServiceImpl sobreService;
    private UsuarioServiceImpl usuarioService;
    private ComprarSobreController comprarSobreController;


    @BeforeEach
    public void setUp() {
        sobreService = Mockito.mock(SobreServiceImpl.class);
        usuarioService = Mockito.mock(UsuarioServiceImpl.class);
//        this.comprarSobreController = new ComprarSobreController(sobreService, usuarioService);
    }

    @Test
    public void testShowViewComprarSobre(){
        SobreServiceImpl sobreService = Mockito.mock(SobreServiceImpl.class);
//        ComprarSobreController comprarSobreController = new ComprarSobreController(sobreService, usuarioService);

        List<SobreDTO> sobresEsperados = new ArrayList<>();
        sobresEsperados.add(new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png"));
        sobresEsperados.add(new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png"));
        sobresEsperados.add(new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "sobreFutCodeOro.png"));
        sobresEsperados.add(new SobreDTO("Sobre Especial", 10000.0, TipoSobre.ESPECIAL, "sobreFutCodeEspecial.png"));

        when(sobreService.obtenerSobresDTO()).thenReturn(sobresEsperados);

        ModelAndView mav = comprarSobreController.mostrarVistaComprarSobres();
        String vista = mav.getViewName();

        assertThat(vista, is("vista-comprar-sobres"));
        assertThat(mav.getModel().get("sobres"), is(sobresEsperados));
    }

//    @Test
//    public void dadoQueCreoUnSobreMeDevuelveUnMensajeDeExito(){
//        SobreDTO sobre = new SobreDTO(TipoSobre.BRONCE);
//        Mockito.when(this.sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);
//
////        HttpSession session =
//        String respuesta = this.comprarSobreController.crearSobre(TipoSobre.BRONCE, mockitoSession().getId);
//
//        assertThat(respuesta, is("Sobre creado con exito"));
//    }
//
//    @Test
//    public void dadoQueElSobreNoSePuedeCrearDevuelveUnMensajeDeError(){
//        Mockito.when(this.sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(null);
//
//        String respuesta = this.comprarSobreController.crearSobre(TipoSobre.BRONCE);
//
//        assertThat(respuesta, is("No se pudo crear el sobre"));
//    }




}

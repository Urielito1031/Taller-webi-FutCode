package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.presentacion.controller.ComprarSobreController;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class ControladorComprarSobreTest {

    @Mock
    private SobreServiceImpl sobreService;

    @Mock
    private ComprarSobreController comprarSobreController;

    @Test
    void testShowViewComprarSobre(){
        SobreServiceImpl sobreService = Mockito.mock(SobreServiceImpl.class);
        ComprarSobreController comprarSobreController = new ComprarSobreController(sobreService);

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

    void testComprarSobre(){

    }


}

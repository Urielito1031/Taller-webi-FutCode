package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ComprarSobreController {

    @Autowired
    private SobreServiceImpl sobreService;

    public ComprarSobreController(SobreServiceImpl sobreService) {
        this.sobreService = sobreService;
    }

    @GetMapping("/comprar-sobres")
    public ModelAndView mostrarVistaComprarSobres(){
        List<SobreDTO> sobres = this.sobreService.obtenerSobresDTO();

        ModelAndView mav = new ModelAndView("vista-comprar-sobres");
        mav.addObject("sobres", sobres);

        return mav;
    }

}

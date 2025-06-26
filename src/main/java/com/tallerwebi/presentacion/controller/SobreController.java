package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SobreController {
    private SobreService sobreService;

    @Autowired
    public SobreController(SobreService sobreService){
        this.sobreService = sobreService;
    }

    @PostMapping("/sobre")
    public ModelAndView getSobre(@RequestParam("tipoDeSobre")TipoSobre tipoSobre) {
        SobreDTO sobre = sobreService.crearSobre(tipoSobre);


        ModelAndView mav = new ModelAndView("sobre");
        mav.addObject("sobre", sobre);
        System.out.println("JUGADORES DEL SOBRE" + sobre.getJugadores());
        return mav;
    }



}

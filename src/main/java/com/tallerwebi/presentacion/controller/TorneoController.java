package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.PartidoDTO2;
import com.tallerwebi.presentacion.dto.TablaGeneralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/torneo")
public class TorneoController {
    private TorneoService torneoService;

    @Autowired
    public TorneoController(TorneoService torneoService){
        this.torneoService=torneoService;
    }

    @GetMapping("/{id}/tabla-general")
    public ModelAndView getTorneo (@PathVariable Long id){
        TablaGeneralDTO tablaGeneral;
        tablaGeneral = torneoService.obtenerTablaGeneral(id);
        ModelAndView mav = new ModelAndView("torneo/tablaGeneral");
        mav.addObject("tablaGeneral", tablaGeneral);
        mav.addObject("id", id);
        return mav;
    }

    @GetMapping("/{id}/calendario")
    public ModelAndView getCalendario(@PathVariable Long id) {
        List<PartidoDTO2> calendario = torneoService.obtenerCalendario(id);
        ModelAndView mav = new ModelAndView("torneo/calendario");
        mav.addObject("calendario", calendario);
        mav.addObject("id", id);
        return mav;
    }

}

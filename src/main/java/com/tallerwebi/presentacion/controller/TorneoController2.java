package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.TorneoService2;
import com.tallerwebi.presentacion.dto.PartidoDTO2;
import com.tallerwebi.presentacion.dto.TablaGeneralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TorneoController2 {
    private TorneoService2 torneoService2;

    @Autowired
    public TorneoController2(TorneoService2 torneoService2){
        this.torneoService2 = torneoService2;
    }

    @GetMapping("/torneo/{id}/tabla-general")
    public ModelAndView getTorneo (@PathVariable Long id){
        TablaGeneralDTO tablaGeneral;
        tablaGeneral = torneoService2.obtenerTablaGeneral(id);
        ModelAndView mav = new ModelAndView("torneo/tablaGeneral");
        mav.addObject("tablaGeneral", tablaGeneral);
        mav.addObject("id", id);
        return mav;
    }

    @GetMapping("/torneo/{id}/calendario")
    public ModelAndView getCalendario(@PathVariable Long id) {
        List<PartidoDTO2> calendario = torneoService2.obtenerCalendario(id);
        ModelAndView mav = new ModelAndView("torneo/calendario");
        mav.addObject("calendario", calendario);
        mav.addObject("id", id);
        return mav;
    }

}

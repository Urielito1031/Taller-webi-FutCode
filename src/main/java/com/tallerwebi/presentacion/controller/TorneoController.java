package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TorneoController {


   private final TorneoService torneoService;
   private final EquipoTorneoService equipoTorneoService;

   @Autowired
   public TorneoController(TorneoService torneoService,EquipoTorneoService equipoTorneoService) {
      this.torneoService = torneoService;
      this.equipoTorneoService = equipoTorneoService;
   }

   @GetMapping(path = "/home")
   public String irAHome(Model model) {
      List<TorneoDTO> torneos = torneoService.getAll();
      if(torneos.isEmpty()){
         model.addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      }
      model.addAttribute("torneos",torneos);
      return "home";
   }
   @GetMapping("/detalle-torneo/{id}")
   public String detalleTorneo(@PathVariable Long id, Model model) {
      TorneoDTO torneo = torneoService.getById(id);
      System.out.println("Detalle torneo: " + torneo);
      List<EquipoTorneoDTO> torneoEquipos = equipoTorneoService.getAllByTorneoId(id);
      model.addAttribute("torneo", torneo);
      model.addAttribute("torneoEquipos", torneoEquipos);
      return "detalle-torneo";
   }


}

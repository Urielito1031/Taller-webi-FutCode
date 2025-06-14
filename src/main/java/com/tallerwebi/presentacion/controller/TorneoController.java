package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TorneoController {


   private final TorneoService service;

   @Autowired
   public TorneoController(TorneoService service) {
      this.service = service;
   }

   @GetMapping(path = "/home")
   public String irAHome(Model model) {
      List<TorneoDTO> torneos = service.getAll();
      if(torneos.isEmpty()){
         model.addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      }
      model.addAttribute("torneos",torneos);
      return "home";
   }

   @GetMapping("/detalle-torneo/{id}")
   public String unirseTorneo(@PathVariable Long id,Model model) {
      TorneoDTO torneo = service.getById(id);
      model.addAttribute("torneo", torneo);

      return "detalle-torneo";
   }
}

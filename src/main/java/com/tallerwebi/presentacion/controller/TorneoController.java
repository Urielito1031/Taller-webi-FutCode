package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TorneoController {


   private final TorneoService service;

   @Autowired
   public TorneoController(TorneoService service) {
      this.service = service;
   }


   @GetMapping("/torneos")
   public String listarTorneos(Model model) {
      List<TorneoDTO> torneos = service.getAll();
      model.addAttribute("torneos", torneos);
      model.addAttribute("tiposFormato", TipoFormato.values());
      return "vista-list-torneos";
   }

   @GetMapping("/detalle-torneo/{id}")
   public String unirseTorneo(@PathVariable Long id,Model model) {
      TorneoDTO torneo = service.getById(id);
      model.addAttribute("torneo", torneo);

      return "detalle-torneo";
   }
}

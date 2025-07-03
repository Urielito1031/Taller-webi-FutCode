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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
      List<EquipoTorneoDTO> torneoEquipos = equipoTorneoService.getAllByTorneoId(id);
      model.addAttribute("torneo", torneo);
      model.addAttribute("torneoEquipos", torneoEquipos);
      return "detalle-torneo";
   }
   @PostMapping("/torneo/{id}/unirse")
   public String unirseATorneo(@PathVariable("id") Long torneoId, RedirectAttributes redirectAttributes) {
      try {
         equipoTorneoService.unirseTorneo(torneoId, 1L); // después lo cambiás por el ID real del equipo del usuario
         redirectAttributes.addFlashAttribute("mensajeTorneo", "¡Te uniste al torneo con éxito!");
      } catch (IllegalArgumentException e) {
         redirectAttributes.addFlashAttribute("errorUnirse", e.getMessage());
      }
      return "redirect:/detalle-torneo/" + torneoId;
   }




}

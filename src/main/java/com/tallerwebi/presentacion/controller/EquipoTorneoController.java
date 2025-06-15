//package com.tallerwebi.presentacion.controller;
//
//import com.tallerwebi.dominio.service.EquipoTorneoService;
//import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//
//@Controller
//public class EquipoTorneoController {
//   private final EquipoTorneoService service;
//
//   @Autowired
//   public EquipoTorneoController(EquipoTorneoService service) {
//      this.service = service;
//   }
//
//
//   @GetMapping("/detalle-torneo/{id}")
//   public String detalleTorneo(@PathVariable Long id,Model model) {
//      List<EquipoTorneoDTO> torneoEquipos = service.getAllByTorneoId(id);
//      System.out.println("EquipoTorneoDTOS : "+torneoEquipos);
//      model.addAttribute("torneoEquipos", torneoEquipos);
//
//      return "detalle-torneo";
//   }
//}

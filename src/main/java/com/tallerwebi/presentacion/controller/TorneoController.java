package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.TorneoServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TorneoController {
   private TorneoServiceImpl service;

   public TorneoController(TorneoServiceImpl service) {
      this.service = service;
   }
   @GetMapping("/torneos")
   public String vistaTorneos(Model model) {
      return "vista-list-torneos";
   }
}

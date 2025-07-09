package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {

   private final TorneoService torneoService;

   @Autowired
   public HomeController(TorneoService torneoService) {
      this.torneoService = torneoService;
   }

   @GetMapping(path = "/home")
   public String irAHome(Model model, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         return "redirect:/login";
      }
      List<TorneoDTO> torneos = torneoService.getAll();
      if (torneos.isEmpty()) {
         model.addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      }
      model.addAttribute("torneos", torneos);
      return "home";
   }

}
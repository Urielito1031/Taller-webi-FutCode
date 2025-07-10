package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
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
   private final UsuarioService usuarioService;

   @Autowired
   public HomeController(TorneoService torneoService, UsuarioService usuarioService) {
      this.torneoService = torneoService;
      this.usuarioService = usuarioService;
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

      Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
      model.addAttribute("usuario", usuario);

      if (usuario.getEquipo() != null) {
         model.addAttribute("equipoNombre", usuario.getEquipo().getNombre());
      } else {
         model.addAttribute("equipoNombre", "Sin equipo");
      }

      model.addAttribute("torneos", torneos);
      return "home";
   }

}